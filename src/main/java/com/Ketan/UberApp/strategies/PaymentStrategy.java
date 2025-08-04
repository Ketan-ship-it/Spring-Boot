package com.Ketan.UberApp.strategies;

import com.Ketan.UberApp.entities.Payment;

public interface PaymentStrategy {
    Double PLATFORM_COMMISSION=0.3;
    void processPayment(Payment payment);
}
