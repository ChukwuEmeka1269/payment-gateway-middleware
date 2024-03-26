package com.emekauk.paymentgatewaymiddleware.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAYMENTS")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionId;
    private BigDecimal amount;
    private String currencyCode;
    private String countryCode;
    private PaymentStatus status;


    public enum PaymentStatus {
        INITIATED, SUCCESSFUL, FAILED
    }
}
