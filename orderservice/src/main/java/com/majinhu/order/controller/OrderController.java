package com.majinhu.order.controller;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName OrderController
 * @Description
 * http://localhost:8081/order/getOrder/1
 * http://localhost:8082/order/getOrder/1
 * @Author majinhu
 * @Date 2020/2/20 17:03
 * @Version 1.0
 **/
@RestController
@RequestMapping("/order")
public class OrderController {
    @RequestMapping("/getOrder/{id}")
    public Object getOrder(HttpServletRequest request, @PathVariable("id") String id){
        int localPort = request.getLocalPort();
        String order = id+"orderName"+localPort;
        return order;
    }
}
