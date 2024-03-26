package com.emekauk.paymentgatewaymiddleware.domain.requests;

import com.emekauk.paymentgatewaymiddleware.domain.entity.Payment.PaymentStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WebhookNotification {
    private String transactionId;
    private PaymentStatus status;
}
