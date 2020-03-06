package com.yk.rabbitmqdemo.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.Map;

public class MyConsumer extends DefaultConsumer {

    private Channel channel;

    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body) throws IOException {
        System.err.println("consumer message---------------");
        System.err.println("consumerTag: " + consumerTag);
        System.err.println("envelope: " + envelope);
        System.err.println("properties: " + properties);
        System.err.println("body: " + new String(body));
//        if(1 == (int)properties.getHeaders().get("num")){
//            //第三个参数表示:重回队列
//            channel.basicNack(envelope.getDeliveryTag(), false, true);
//        }else {
//            channel.basicAck(envelope.getDeliveryTag(), false);
//        }
    }
}
