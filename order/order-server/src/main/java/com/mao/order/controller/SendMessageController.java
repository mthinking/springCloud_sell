package com.mao.order.controller;

import com.mao.order.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {

    @Autowired
    //private StreamClient streamClient;
//
//    @GetMapping("/sendMessage")
//    public void  process(){
//        streamClient.output().send(MessageBuilder.withPayload("now "+ new Date()).build());
//    }

    @GetMapping("/sendMessage")
    public void  process(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("123456778");
   //    streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }
}
