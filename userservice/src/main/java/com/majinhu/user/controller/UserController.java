package com.majinhu.user.controller;

import com.majinhu.user.utils.LoadBalance;
import com.majinhu.user.utils.RandomLoadBalance;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName OrderController
 * @Description
   http://localhost:9090/users/getUser/1
 * @Author majinhu
 * @Date 2020/2/20 17:03
 * @Version 1.0
 **/
@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private RestTemplate restTemplate;
    private LoadBalance loadBalance = new RandomLoadBalance();
    @RequestMapping("/getUser/{id}")
    public Object getOrder(HttpServletRequest request, @PathVariable("id") String id){
        //用户服务调用订单服务，随机选择一个订单服务的地址。
        String host = loadBalance.choseServerHost();
        String order ="";
        if(host == null|| StringUtils.isEmpty(host)){
            System.out.println("服务停止了");
            order = "服务停止了";
        }else{
            order = restTemplate.getForObject("http://" + host + "order/getOrder/1", String.class);
            System.out.println("http://" + host +"order/getOrder/1");
        }
        return order;

    }
}
