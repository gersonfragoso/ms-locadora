package com.solutis.locadora.payment_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${payment-service.url}")
    private String paymentServiceUrl; // URL do serviço de pagamento

    private final NotificationProducer notificationProducer; // Para envio de notificações

    // Construtor para injeção de dependências
    public PaymentService(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    //Outros métodos..
}
