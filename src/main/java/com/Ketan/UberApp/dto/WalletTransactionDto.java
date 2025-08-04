package com.Ketan.UberApp.dto;

import com.Ketan.UberApp.entities.enums.TransactionMethod;
import com.Ketan.UberApp.entities.enums.TransactionType;

import java.time.LocalDateTime;

public class WalletTransactionDto {

    private Long id;

    private Double Amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private RideDto ride;

    private String transactionId;

    private LocalDateTime timestamp;

    private WalletDto wallet;

}
