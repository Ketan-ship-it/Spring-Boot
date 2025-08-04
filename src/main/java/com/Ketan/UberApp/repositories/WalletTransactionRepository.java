package com.Ketan.UberApp.repositories;

import com.Ketan.UberApp.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction,Long> {
}
