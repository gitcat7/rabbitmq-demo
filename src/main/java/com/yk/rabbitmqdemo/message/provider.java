package com.yk.rabbitmqdemo.message;

import com.rabbitmq.client.*;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class provider {

    public static void main(String[] args) throws IOException, TimeoutException {

        String exchangeName = "test_exchange";
        String routingKey = "test_key";
        String queueName = "test321";

        //1.创建一个connectFactory
        ConnectionFactory factory = new ConnectionFactory();
        //设置地址,端口,虚拟机主机
        factory.setHost("120.55.171.239");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        //2.通过连接工厂创建连接
        Connection connection = factory.newConnection();

        //3.通过connect创建一个channel
        Channel channel = connection.createChannel();

        //自定义属性
        Map<String, Object> heards = new HashMap<>();
        heards.put("h1", "111");
        heards.put("h2", "222");

        //deliveryMode的参数:2表示持久化,1表示不持久化
        //expiration表示过期时间
        //headers为自定义参数
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                                                                    .deliveryMode(2)
                                                                    .contentEncoding("utf-8")
                                                                    .expiration("10000")
                                                                    .headers(heards)
                                                                    .build();

        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, true, null);

        channel.queueDeclare(queueName, true, false, false, null);

        //4.通过channel发送数据
        String msg = "hello";
        channel.basicPublish(exchangeName, routingKey, properties, msg.getBytes());
        System.out.println("server发送消息：" + msg);
        channel.close();
        connection.close();
    }
}
