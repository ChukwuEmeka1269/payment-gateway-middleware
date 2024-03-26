package com.emekauk.paymentgatewaymiddleware.downstreamService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankServicePaymentResponse {
    private String transactionId;
    private String reference;
    private String description;
    private Status status;

    public enum Status{
        IP, PS, PF
    }
}
