package com.majinhu.user.listerner;

import com.majinhu.user.utils.LoadBalance;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ClientInitListener
 * @Description 通过@Component+  @PostConstruct 实现bean初始化注册。
 * @Author majinhu
 * @Date 2020/2/20 16:21
 * @Version 1.0
 **/
@Component
public class ClientInitListener2  {
    private static final String BASE_SERVICES ="/services";
    private static final String SERVICE_NAME ="/orders";
    private ZooKeeper zooKeeper;

    @PostConstruct
    private  void init(){
        try {
            //连接到zk，获得列表信息
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, watchedEvent -> {
                if (watchedEvent.getType() == Watcher.Event.EventType.NodeChildrenChanged &&
                        watchedEvent.getPath().equals(BASE_SERVICES + SERVICE_NAME)) {
                    //节点变化时候获取列表信息。
                    System.out.println("观察到节点发生了改变");
                    updateServerList();
                }
            });
            //第一次连接时候获取列表信息。
            updateServerList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void updateServerList(){
        List<String> newServerList = new ArrayList<>();
        try {
            //获取根路径下的到所有的节点信息。
            List<String> children = zooKeeper.getChildren(BASE_SERVICES + SERVICE_NAME, true);
            for (String subNode:children) {
                byte[] data = zooKeeper.getData(BASE_SERVICES + SERVICE_NAME + "/" + subNode, false, null);
                String host = new String(data, "UTF-8");
                System.out.println("host:"+host);
                //读取到本地列表list中
                newServerList.add(host);
            }
            //读取地址列表到内变量SERVICE_LIST。
            LoadBalance.SERVICE_LIST =  newServerList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
