package com.majinhu.user.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * @ClassName ServerResiter
 * @Description 服务端注册中心。用户服务暂时用不到。
 *  <dependency>
 *             <groupId>org.apache.zookeeper</groupId>
 *             <artifactId>zookeeper</artifactId>
 *             <version>3.4.9</version>
 *         </dependency>
 * @Author majinhu
 * @Date 2020/2/20 15:44
 * @Version 1.0
 **/
public class ServerResiter {
    private static final String BASE_SERVICES ="/services";
    private static final String SERVICE_NAME ="/orders";
    //把ip地址和服务端口注册到zk
    public static void register(String address,int port){

        try {
            String path = BASE_SERVICES + SERVICE_NAME;
            //获取一个zk客户端，待监听模式的。
            ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, watchedEvent -> {
            });
            //判断根目录是否已经存在了。不存在则创建。创建根目录
            Stat exists = zooKeeper.exists(path, false);
            if(exists == null){
                //创建一个节点。永久节点。值为deer。
                zooKeeper.create(path,"majinhu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            String server_path = address +":"+ port;
            //创建一个临时顺序节点。
            zooKeeper.create(path+"/child",server_path.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("订单服务注册成功");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
