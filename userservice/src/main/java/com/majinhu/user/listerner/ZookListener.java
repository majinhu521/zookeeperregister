package com.majinhu.user.listerner;

import com.majinhu.user.utils.LoadBalance;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ClientInitListener
 * @Description 通过ServletContextListener 实现bean初始化注册。启动的时候自动执行。
 * 重写2个方法：contextInitialized() contextDestroyed()
 * 注意： zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, (watchedEvent) -> {
 * 而不是  zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, watchedEvent -> {
 * @Author majinhu
 * @Date 2020/2/20 16:21
 * @Version 1.0
 **/
public class ZookListener  {
    private static final String BASE_SERVICES ="/services";
    private static final String SERVICE_NAME ="/orders";
    private ZooKeeper zooKeeper;

   public void init(){
        try {
            //连接到zk，获得列表信息
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, watchedEvent ->{
                    if ((watchedEvent.getType() == Watcher.Event.EventType.NodeChildrenChanged ||
                            watchedEvent.getType() == Watcher.Event.EventType.NodeDataChanged)
                            &&watchedEvent.getPath().equals(BASE_SERVICES + SERVICE_NAME)) {
                        //节点变化时候获取列表信息。
                        System.out.println("观察到节点列表发生了改变（添加或者删除节点）");
                        updateServerList();
                    }

                });

            //第一次连接时候获取列表信息。
            System.out.println("第一次连接时候获取列表信息");
            updateServerList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //终于知道为什么不能观察了。zooKeeper.close();不能进行关闭，否则不能观察我擦。搞了一个上午了。
       // 需要建立一个长连接。
//        finally {
//            try {
//                zooKeeper.close();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
    private void updateServerList(){
        List<String> newServerList = new ArrayList<>();
        try {
            //获取根路径下的到所有的节点信息。如果节点有变化，增加或者减少。则进行监控。
            List<String> children = zooKeeper.getChildren(BASE_SERVICES + SERVICE_NAME, true);
            for (String subNode:children) {//值有变化不进行监控。
                byte[] data = zooKeeper.getData(BASE_SERVICES + SERVICE_NAME + "/" + subNode, true, null);
                String host = new String(data, "UTF-8");
                System.out.println("host"+host);
                //读取到本地列表list中
                newServerList.add(host);
            }
            //随机选一个地址。
            LoadBalance.SERVICE_LIST =  newServerList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
