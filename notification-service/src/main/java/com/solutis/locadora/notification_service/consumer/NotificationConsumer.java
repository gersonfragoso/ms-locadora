package com.solutis.locadora.notification_service.consumer;

import com.solutis.locadora.notification_service.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void receiveMessage(String message){
        System.out.println("Mensagem recebida:" + message);

        notificationService.sendEmailNotification(message);
    }
}
