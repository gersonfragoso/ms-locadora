package com.solutis.locadora.customer_service.producer;

import com.solutis.locadora.customer_service.model.NotificationRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.email.name}")
    private String emailQueue;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Método para enviar uma notificação
    public void sendNotification(String name, String email) {
        // Criar uma mensagem de notificação
        String message = "Bem-vindo, " + name + "! Sua conta foi criada com sucesso!";

        // Enviar a mensagem para a fila do RabbitMQ
        rabbitTemplate.convertAndSend(emailQueue, message);
    }
}
