package com.solutis.locadora.notification_service.controller;

import com.solutis.locadora.notification_service.model.NotificationRequest;
import com.solutis.locadora.notification_service.producer.NotificationProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationProducer messageProducer;

    public NotificationController(NotificationProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/welcome")
    public ResponseEntity<String> sendWelcomeNotification(@RequestBody NotificationRequest notificationRequest){
        String message = "Bem-vindo, " + notificationRequest.getName() + "! Sua conta foi criada com sucesso!";
        messageProducer.sendMessage(message);
        return ResponseEntity.ok("Notificação de boas-vindas enviada.");
    }
}
