package com.yk.rabbitmqdemo.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
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
        String queueName = "test321_consumer";
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
        String exchangeName = "test_exchange_consumer";
        String routingKey = "test_key_consumer";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("120.55.171.239");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName,true, false, false, null);
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, true, null);
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.basicConsume(queueName, true, new MyConsumer(channel));

    }
}
