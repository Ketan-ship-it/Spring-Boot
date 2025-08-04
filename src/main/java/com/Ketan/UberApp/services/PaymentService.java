package com.Ketan.UberApp.services;

import com.Ketan.UberApp.entities.Payment;
import com.Ketan.UberApp.entities.Ride;
import com.Ketan.UberApp.entities.enums.PaymentStatus;

public interface PaymentService {
    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus status);
}
