package com.yk.rabbitmqdemo.returnlistener;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class Provider {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setHost("120.55.171.239");
        factory.setPort(5672);

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        //启动消息的确认模式
        channel.confirmSelect();

        String exchangeName = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "abc.save";
        String msg = "hello";

        channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKeyError, true, null, (msg + "1").getBytes());

        System.out.println("server发送成功");

        //添加一个return监听
        channel.addReturnListener(aReturn -> {
            System.out.println(new String(aReturn.getBody()));
        });

    }
}
