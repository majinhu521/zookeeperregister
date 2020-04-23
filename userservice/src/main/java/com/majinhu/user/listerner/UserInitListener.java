package com.majinhu.user.listerner;




import com.majinhu.user.zk.ServerResiter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.InetAddress;
import java.util.Properties;

/**
 * @ClassName Initlistener
 * @Description
 * https://blog.csdn.net/weixin_34347651/article/details/93749179?utm_source=distribute.pc_relevant.none-task
 * @Author majinhu
 * @Date 2020/2/20 15:02
 * @Version 1.0
 **/

public class UserInitListener implements ServletContextListener {

    /** springboot启动类需要加 @ServletComponentScan
     当Servlet 容器启动或终止Web 应用时，会触发ServletContextEvent 事件，
     该事件由ServletContextListener 来处理。
     在 ServletContextListener 接口中定义了处理ServletContextEvent 事件的两个方法。
     **/

     /**
      * 当Servlet 容器启动Web 应用时调用该方法。在调用完该方法之后，容器再对Filter 初始化，
      * 并且对那些在Web 应用启动时就需要被初始化的Servlet 进行初始化。
      */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            //读取配置文件中的port端口，加载到注册中心。
            Properties properties = new Properties();
            properties.load(UserInitListener.class.getClassLoader().getResourceAsStream("application.properties"));
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            int port = Integer.valueOf(properties.getProperty("server.port"));
            ServerResiter.register(hostAddress,port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
      当Servlet 容器终止Web 应用时调用该方法。
     在调用该方法之前，容器会先销毁所有的Servlet 和Filter 过滤器。
    **/
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
