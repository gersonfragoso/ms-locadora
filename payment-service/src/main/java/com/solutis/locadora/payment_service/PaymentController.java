package com.solutis.locadora.payment_service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @PostMapping("/process-payment")
    public String processPayment(@RequestBody PaymentRequest paymentRequest) {
        // Lógica de processamento de pagamento
        return "Pagamento processado com sucesso";
    }

}
