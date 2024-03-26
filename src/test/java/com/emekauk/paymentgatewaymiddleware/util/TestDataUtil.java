package com.emekauk.paymentgatewaymiddleware.util;

import com.emekauk.paymentgatewaymiddleware.domain.entity.Payment;
import com.emekauk.paymentgatewaymiddleware.domain.requests.PaymentRequest;
import com.emekauk.paymentgatewaymiddleware.domain.requests.WebhookNotification;
import com.emekauk.paymentgatewaymiddleware.domain.responses.PaymentResponse;

import java.math.BigDecimal;

public class TestDataUtil {

    private TestDataUtil(){}

    public static PaymentResponse successfulPaymentResponse(){
        return PaymentResponse.builder()
                .transactionId("2ea8e702-0a5b-4feb-b1c6-ba40f4502ad6")
                .status(Payment.PaymentStatus.SUCCESSFUL)
                .amount(BigDecimal.valueOf(1500.45))
                .build();
    }

    public static PaymentResponse failedPaymentResponse(){
        return PaymentResponse.builder()
                .transactionId("2ea8e702-0a5b-4feb-b1c6-ba40f4502ad6")
                .status(Payment.PaymentStatus.FAILED)
                .amount(BigDecimal.valueOf(1500.45))
                .build();
    }

    public static PaymentResponse initiatedPaymentResponse(){
        return PaymentResponse.builder()
                .transactionId("2ea8e702-0a5b-4feb-b1c6-ba40f4502ad6")
                .status(Payment.PaymentStatus.INITIATED)
                .amount(BigDecimal.valueOf(1500.45))
                .build();
    }


    public static PaymentRequest paymentRequest(){
        return PaymentRequest.builder()
                .reference("BOX123")
                .currencyCode("NGN")
                .countryCode("NG")
                .amount(BigDecimal.valueOf(1500.45))
                .build();
    }

    public static WebhookNotification successfulWebhookNotification(){
        return WebhookNotification.builder()
                .status(Payment.PaymentStatus.SUCCESSFUL)
                .transactionId("2ea8e702-0a5b-4feb-b1c6-ba40f4502ad6")
                .build();
    }

    public static WebhookNotification failedWebhookNotification(){
        return WebhookNotification.builder()
                .status(Payment.PaymentStatus.FAILED)
                .transactionId("2ea8e702-0a5b-4feb-b1c6-ba40f4502ad6")
                .build();
    }

}
