package com.yk.rabbitmqdemo.returnlistener;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setHost("120.55.171.239");
        factory.setPort(5672);

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_return_exchange";
        String routingKey = "return.#";
        String queueName = "test_return_queue";

        //声明交换机,队列,绑定
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        DeliverCallback deliverCallback = (s, delivery) -> {
            String msg = new String(delivery.getBody());
            System.out.println("client接收的消息:" + msg);
        };
        channel.basicConsume(queueName, true, deliverCallback, (CancelCallback) null);

    }
}
