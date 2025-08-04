package com.Ketan.UberApp.dto;

import com.Ketan.UberApp.entities.User;

import java.util.List;

public class WalletDto {

    private Long id;

    private User user;

    private Double balance;

    private List<WalletTransactionDto> walletTransaction;

}
