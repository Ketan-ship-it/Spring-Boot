package com.Ketan.UberApp.strategies.stratagiesimplementation;

import com.Ketan.UberApp.entities.Driver;
import com.Ketan.UberApp.entities.Payment;
import com.Ketan.UberApp.entities.Wallet;
import com.Ketan.UberApp.entities.enums.PaymentStatus;
import com.Ketan.UberApp.entities.enums.TransactionMethod;
import com.Ketan.UberApp.repositories.PaymentRepository;
import com.Ketan.UberApp.services.WalletService;
import com.Ketan.UberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    //Rider pays - 100
    //Platform fee is automatically deducted from driver's wallet - 30
    //Now Driver has all the money in cash with him
    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Wallet driverwallet = walletService.findByUser(driver.getUser());
        double platform_fee = payment.getAmount()*PLATFORM_COMMISSION;
        walletService.deductMoneyFromWallet(driver.getUser(), platform_fee,"",payment.getRide(), TransactionMethod.RIDE);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
