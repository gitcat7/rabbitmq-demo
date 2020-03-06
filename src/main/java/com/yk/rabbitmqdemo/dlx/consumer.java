package com.yk.rabbitmqdemo.dlx;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.yk.rabbitmqdemo.consumer.MyConsumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
//        //1.创建一个connectFactory
//        ConnectionFactory factory = new ConnectionFactory();
//        //设置地址,端口,虚拟机主机
//        factory.setHost("120.55.171.239");
//        factory.setPort(5672);
//        factory.setVirtualHost("/");
//
//        //2.通过连接工厂创建连接
//        Connection connection = factory.newConnection();
//
//        //3.通过connect创建一个channel
//        Channel channel = connection.createChannel();
//
//        //4.声明队列
        String queueName = "test321_dlx";
//        channel.queueDeclare(queueName, true, false, false, null);
//
////        //5.创建消费者
////        DefaultConsumer consumer = new DefaultConsumer(channel);
////
////        //6.设置channel
////
////        //7.获取消息
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            long deliveryTag = delivery.getEnvelope().getDeliveryTag();
//            String msg = new String(delivery.getBody());
//            System.out.println("client接收消息：" + msg + ",deliveryTag:" + deliveryTag);
//        };
//        channel.basicConsume(queueName, true, deliverCallback, (CancelCallback) null);
        String exchangeName = "test_exchange_dlx";
        String routingKey = "dlx.#";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("120.55.171.239");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "dlx.exchange");
        channel.queueDeclare(queueName,true, false, false, arguments);
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true, true, null);
        //声明死信队列
        channel.exchangeDeclare("dlx.exchange", BuiltinExchangeType.TOPIC, true, false, null);
        channel.queueDeclare("dlx.queue", true, false, false, null);
        channel.queueBind("dlx.queue", "dlx.exchange", "#");
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.basicConsume(queueName, true, new MyConsumer(channel));

    }
}
