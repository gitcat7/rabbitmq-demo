package com.yk.rabbitmqdemo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test(){
        String beanName = rabbitAdmin.getBeanName();
        System.out.println(beanName);
    }

    @Test
    public void testSendMessage() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc", "123");
        String msg = "hello";
        Message message = new Message(msg.getBytes(), messageProperties);

        rabbitTemplate.convertSendAndReceive("test_exchange", "test_key", message, message1 -> {
            System.err.println("添加额外的设置");
            message1.getMessageProperties().getHeaders().put("desc", "111");
            message1.getMessageProperties().getHeaders().put("test", "额外新加的属性");
            return message1;
        });
    }

    @Test
    public void testSendMessage2() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("utf-8");
        String msg = "hello111";
        Message message = new Message(msg.getBytes(), messageProperties);

        rabbitTemplate.convertSendAndReceive("test_exchange", "test_key", message/*, message1 -> {
            System.err.println("添加额外的设置");
            message1.getMessageProperties().getHeaders().put("desc", "111");
            message1.getMessageProperties().getHeaders().put("test", "额外新加的属性");
            return message1;
        }*/);
    }

}
