package com.emekauk.paymentgatewaymiddleware.repositories;

import com.emekauk.paymentgatewaymiddleware.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findPaymentByTransactionId(String transactionId);
}
