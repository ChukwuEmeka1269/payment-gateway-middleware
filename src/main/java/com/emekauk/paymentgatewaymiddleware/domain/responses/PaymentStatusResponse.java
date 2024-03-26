package com.emekauk.paymentgatewaymiddleware.domain.responses;


import com.emekauk.paymentgatewaymiddleware.domain.entity.Payment.PaymentStatus;
import lombok.Data;

@Data
public class PaymentStatusResponse {
    private PaymentStatus status;
}
