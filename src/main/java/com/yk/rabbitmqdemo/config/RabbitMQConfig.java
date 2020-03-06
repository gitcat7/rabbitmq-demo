package com.yk.rabbitmqdemo.config;

import com.yk.rabbitmqdemo.constant.RabbitMQConstant;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    //创建一个消息队列
    @Bean
    public Queue immediateQueue(){
        //true:表示持久化
        return new Queue(RabbitMQConstant.QUEUE_NAME,true);
    }

//    @Bean
//    public DirectExchange immediateExchange(){
//        return new DirectExchange();
//    }
}
