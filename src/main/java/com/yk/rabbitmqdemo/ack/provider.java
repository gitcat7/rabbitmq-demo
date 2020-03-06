package com.yk.rabbitmqdemo.ack;
import	java.util.HashMap;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class provider {

    public static void main(String[] args) throws IOException, TimeoutException {

        String exchangeName = "test_exchange_ack";
        String routingKey = "test_key_ack";
        String queueName = "test321_ack";

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

        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, true, null);

        channel.queueDeclare(queueName, true, false, false, null);

        Map<String, Object> header = new HashMap<> ();
        for (int i = 0; i < 5; i++) {
            header.put("num", i);
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)
                    .contentEncoding("utf-8")
                    .headers(header)
                    .build();

            //4.通过channel发送数据
            String msg = "hello" + i;
            channel.basicPublish(exchangeName, routingKey, properties, msg.getBytes());
            System.out.println("server发送消息：" + msg);
        }
    }
}
