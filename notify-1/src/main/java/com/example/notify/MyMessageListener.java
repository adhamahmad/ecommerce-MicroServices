package com.example.notify;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyMessageListener {
    @Autowired
    private MessageController messageController;

    @RabbitListener(queues = "${spring.rabbitmq.listener.simple.queue-name}")
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
        messageController.setMessage(message);
    }
}
