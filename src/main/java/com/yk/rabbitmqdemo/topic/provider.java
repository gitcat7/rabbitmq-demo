package com.yk.rabbitmqdemo.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class provider {

    public static void main(String[] args) throws IOException, TimeoutException {

        String exchangeName = "test_exchange_topic";
        String routingKey1 = "test.key";
        String routingKey2 = "test.key.topic2";
        String routingKey3 = "test.key.topic3";
        String queueName = "test321_topic";

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

        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true, true, null);

        channel.queueDeclare(queueName, true, false, false, null);

        //4.通过channel发送数据
        String msg = "hello";
        channel.basicPublish(exchangeName, routingKey1, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKey2, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKey3, null, msg.getBytes());
        System.out.println("server发送消息：" + msg);
        channel.close();
        connection.close();
    }
}
