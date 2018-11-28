package com.mao.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;


@Component
@EnableBinding({StreamClient.class})
@Slf4j
public class StreamReceiver {

//    @StreamListener(StreamClient.INPUT)
//    @SendTo(StreamClient.OUTPUT)
//    public Object processInput(Object message){
//        log.info("StreamINPUTReceiver:{}",message);
//        return message;
//    }

//    @StreamListener(StreamClientOutPut.OUTPUT)
//    public void processOutput(Object message){
//        log.info("StreamOutPutReceiver:{}",message);
//    }

    /**
     * 接收OrderDTO对象 消息
     *
     * @param message
     */
//    @StreamListener(value = StreamClient.OUTPUT)
//    public void processOutput(Object message) {
//        log.info("StreamOrderDTOReceiver:{}", message);
//    }
}
