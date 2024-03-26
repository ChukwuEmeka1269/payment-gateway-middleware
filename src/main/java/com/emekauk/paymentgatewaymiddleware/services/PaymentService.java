package com.emekauk.paymentgatewaymiddleware.services;

import com.emekauk.paymentgatewaymiddleware.domain.entity.Payment;
import com.emekauk.paymentgatewaymiddleware.domain.requests.PaymentRequest;
import com.emekauk.paymentgatewaymiddleware.domain.requests.WebhookNotification;
import com.emekauk.paymentgatewaymiddleware.domain.responses.PaymentResponse;
import com.emekauk.paymentgatewaymiddleware.domain.responses.PaymentStatusResponse;


public interface PaymentService {

    PaymentResponse initiatePayment(PaymentRequest paymentRequest);

    PaymentStatusResponse checkPaymentStatus(String transactionId);

    void processWebhookNotification(WebhookNotification notification);
}
