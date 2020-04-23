import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName ZKtest
 * @Description ZooKeeper 监听
 * https://blog.csdn.net/pange1991/article/details/86675426
 * https://www.cnblogs.com/shamo89/p/9787176.html
 * https://blog.csdn.net/xiaoliuliu2050/article/details/82500312
 * @Author majinhu
 * @Date 2020/2/21 9:59
 * @Version 1.0
 **/
public class ZKtest {
    private String path ="/testNode";
    @Test
    public void createNode() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, watchedEvent -> {
        });
        //判断根目录是否已经存在了。不存在则创建。创建根目录
        Stat exists = null;

        exists = zooKeeper.exists(path, false);

        if (exists == null) {
            //创建一个节点。永久节点。值为deer。
            zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

    }
    @Test
    public void createNodeWatch() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getType() == Event.EventType.NodeDeleted && path.equals(watchedEvent.getPath())){
                    System.out.println("观察到节点被删除了");
                }
                if(watchedEvent.getType() == Event.EventType.NodeDataChanged && path.equals(watchedEvent.getPath())){
                    System.out.println("观察到节点被修改了");
                }
            }
        });
        //判断根目录是否已经存在了。
        Stat exists = zooKeeper.exists(path, true);//watch需要设置成true

        if (exists != null) {
            //delete一个节点。
//            zooKeeper.delete(path,-1);
            zooKeeper.setData(path,"123".getBytes(),-1);
        }
    }
}
