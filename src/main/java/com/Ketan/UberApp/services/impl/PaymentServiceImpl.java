package com.Ketan.UberApp.services.impl;

import com.Ketan.UberApp.entities.Payment;
import com.Ketan.UberApp.entities.Ride;
import com.Ketan.UberApp.entities.enums.PaymentStatus;
import com.Ketan.UberApp.repositories.PaymentRepository;
import com.Ketan.UberApp.services.PaymentService;
import com.Ketan.UberApp.strategies.StrategyManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StrategyManager strategyManager;

    @Override
    public void processPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride).orElseThrow(()-> new RuntimeException("No such ride is there"));
        strategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment = Payment.builder()
                .ride(ride)
                .Amount(ride.getFare())
                .paymentMethod(ride.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
    }
}
