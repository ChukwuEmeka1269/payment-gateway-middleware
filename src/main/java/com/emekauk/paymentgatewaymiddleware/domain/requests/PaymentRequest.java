package com.emekauk.paymentgatewaymiddleware.domain.requests;

import jakarta.validation.constraints.DecimalMin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PaymentRequest {

    @NotNull(message = "Amount value cannot be null")
    @DecimalMin(value = "0.50", message = "Amount must be greater than or equal to 0.50")
    private BigDecimal amount;

    @NotBlank(message = "Currency Code cannot be blank. Please specify the currency code for this transaction.")
    private String currencyCode;

    @NotBlank(message = "Country code cannot be blank. Please specify the country code for this transaction.")
    private String countryCode;

    @NotBlank(message = "Please provide a unique reference for this payment.")
    private String reference; //This reference uniquely identifies who or what the payment is for
}
