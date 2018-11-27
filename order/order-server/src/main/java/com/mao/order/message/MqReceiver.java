package com.mao.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收MQ消息
 */
@Slf4j
@Component
public class MqReceiver {

    //1.第一种：创建好队列myqueue，然后接收消息
    //@RabbitListener(queues = "myQueue")
    //2.自动创建队列
    //@RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //3.自动创建，Exchange和Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String message){
        log.info("MqReceiver:{}",message);
    }

    /**
     * 水果 供应商服务 接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("fruitOrder"),
            key = "fruit",
            exchange = @Exchange("myOrder")
    ))
    public void processFruit(String message){
        log.info("fruit MqReceiver:{}",message);
    }


    /**
     * 数码供应商服务 接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("computerOrder"),
            key = "computer",
            exchange = @Exchange("myOrder")
    ))
    public void processComputer(String message){
        log.info("computer MqReceiver:{}",message);
    }
}
