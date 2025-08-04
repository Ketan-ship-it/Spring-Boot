package com.Ketan.UberApp.strategies.stratagiesimplementation;

import com.Ketan.UberApp.entities.Driver;
import com.Ketan.UberApp.entities.Payment;
import com.Ketan.UberApp.entities.Rider;
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
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private  final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(),null,payment.getRide(),TransactionMethod.RIDE);

        double driversCut = payment.getAmount() * (1-PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(), driversCut,null,payment.getRide(),TransactionMethod.RIDE);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
