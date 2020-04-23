package com.majinhu;

import com.majinhu.user.listerner.ClientInitListener;
import com.majinhu.user.listerner.InitListener;
import com.majinhu.user.listerner.ZookListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName orderApplication
 * @Description
 * https://www.jianshu.com/p/1d3385b0c54c
 * @Author majinhu
 * @Date 2020/2/20 14:56
 * @Version 1.0
 **/
@SpringBootApplication
@ServletComponentScan
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate(){return  new RestTemplate();}

//    @Bean
//    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
//        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
//        servletListenerRegistrationBean.setListener(new InitListener());
//        return servletListenerRegistrationBean;
//    }

//    @Bean
//    public ZookListener zookListener(){
//        ZookListener listener = new ZookListener();
//        listener.init();
//        return listener;
//    }

}
