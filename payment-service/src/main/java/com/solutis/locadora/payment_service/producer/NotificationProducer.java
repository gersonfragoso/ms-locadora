package com.solutis.locadora.payment_service.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String emailQueue;

    public NotificationProducer(RabbitTemplate rabbitTemplate, @Value("${broker.queue.email.name}") String emailQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.emailQueue = emailQueue;
    }

    // Método para enviar uma mensagem de notificação
    public void sendNotification(String message) {
        rabbitTemplate.convertAndSend(emailQueue, message);
        System.out.println("Mensagem enviada: " + message);
    }

}
