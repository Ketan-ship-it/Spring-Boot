package com.Ketan.UberApp.repositories;

import com.Ketan.UberApp.entities.User;
import com.Ketan.UberApp.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findByUser(User user);
}
