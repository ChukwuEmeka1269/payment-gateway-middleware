package com.emekauk.paymentgatewaymiddleware.domain.responses;


import com.emekauk.paymentgatewaymiddleware.domain.entity.Payment.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PaymentResponse {

    private BigDecimal amount;
    private String transactionId;
    private PaymentStatus status;

}
