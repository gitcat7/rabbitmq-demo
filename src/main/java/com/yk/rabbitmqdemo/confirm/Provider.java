package com.yk.rabbitmqdemo.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
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

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm_save";
        String msg = "hello";

        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        System.out.println("server发送成功");

        //添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            //成功
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                System.out.println("ack--------------");
            }

            //失败
            @Override
            public void handleNack(long l, boolean b) throws IOException {
                System.out.println("no ack--------------");
            }
        });

    }
}
