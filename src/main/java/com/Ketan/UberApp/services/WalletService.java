package com.Ketan.UberApp.services;

import com.Ketan.UberApp.entities.Ride;
import com.Ketan.UberApp.entities.User;
import com.Ketan.UberApp.entities.Wallet;
import com.Ketan.UberApp.entities.enums.TransactionMethod;

public interface WalletService {

    Wallet addMoneyToWallet(User user, Double Amount, String TransactionId, Ride ride, TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    Wallet deductMoneyFromWallet(User user,Double amount,String TransactionId, Ride ride, TransactionMethod transactionMethod);

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);
}
