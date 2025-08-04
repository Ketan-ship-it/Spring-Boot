package com.Ketan.UberApp.services.impl;

import com.Ketan.UberApp.entities.Ride;
import com.Ketan.UberApp.entities.User;
import com.Ketan.UberApp.entities.Wallet;
import com.Ketan.UberApp.entities.WalletTransaction;
import com.Ketan.UberApp.entities.enums.TransactionMethod;
import com.Ketan.UberApp.entities.enums.TransactionType;
import com.Ketan.UberApp.exceptions.InsufficientFunds;
import com.Ketan.UberApp.exceptions.ResourceNotFoundException;
import com.Ketan.UberApp.repositories.WalletRepository;
import com.Ketan.UberApp.services.WalletService;
import com.Ketan.UberApp.services.WalletTransactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double Amount, String transactionId, Ride ride , TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()+Amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDITED)
                .transactionMethod(transactionMethod)
                .amount(Amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {

    }

    @Override
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        if(wallet.getBalance()<amount){
            throw new InsufficientFunds("Not enough money in wallet");
        }
        wallet.setBalance(wallet.getBalance()-amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBITED)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

//        walletTransactionService.createNewWalletTransaction(walletTransaction);

        wallet.getWalletTransaction().add(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow(
                ()-> new ResourceNotFoundException("Wallet With Id "+ walletId + "not found")
        );
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user).orElseThrow(
                ()->new ResourceNotFoundException("User "+ user.getName() + "not found")
                );
    }
}
