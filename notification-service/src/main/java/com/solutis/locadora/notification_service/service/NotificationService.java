package com.solutis.locadora.notification_service.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendEmailNotification(String message) {
        // lógica de envio de email JavaMailSender

        System.out.println("Email enviado com sucesso, mensagem enviada: " + message);
    }
}