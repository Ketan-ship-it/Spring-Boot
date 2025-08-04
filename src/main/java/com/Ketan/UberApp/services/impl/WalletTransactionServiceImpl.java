package com.Ketan.UberApp.services.impl;

import com.Ketan.UberApp.entities.WalletTransaction;
import com.Ketan.UberApp.repositories.WalletTransactionRepository;
import com.Ketan.UberApp.services.WalletTransactionService;
import com.Ketan.UberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WalletTransactionServiceImpl  implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }
}
