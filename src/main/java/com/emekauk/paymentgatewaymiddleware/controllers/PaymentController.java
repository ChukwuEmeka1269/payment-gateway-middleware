package com.emekauk.paymentgatewaymiddleware.controllers;

import com.emekauk.paymentgatewaymiddleware.domain.entity.Payment;
import com.emekauk.paymentgatewaymiddleware.domain.requests.PaymentRequest;
import com.emekauk.paymentgatewaymiddleware.domain.requests.WebhookNotification;
import com.emekauk.paymentgatewaymiddleware.domain.responses.PaymentResponse;
import com.emekauk.paymentgatewaymiddleware.domain.responses.PaymentStatusResponse;
import com.emekauk.paymentgatewaymiddleware.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.emekauk.paymentgatewaymiddleware.utils.ApiUrls.*;

@Tag(name = "Payment", description = "Payment Gateway Middleware APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping(PAYMENT)
public class PaymentController {

    private final PaymentService paymentService;


    @Operation(
            summary = "Initiate a Payment ",
            description = "Initiate payment identified by a unique reference.",
            tags = { "Payment", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Payment.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(INITIATE)
    public ResponseEntity<PaymentResponse> initiatePayment(@Validated @RequestBody PaymentRequest paymentRequest){
        PaymentResponse paymentResponse = paymentService.initiatePayment(paymentRequest);

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Check Status of  a Payment ",
            description = "Checks the status of an initiated payment and returns the updated status",
            tags = { "Check", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Payment.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(STATUS)
    public ResponseEntity<PaymentStatusResponse> checkPaymentStatus(@PathVariable String transactionId){
        PaymentStatusResponse paymentStatusResponse = paymentService.checkPaymentStatus(transactionId);
        return new ResponseEntity<>(paymentStatusResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Webhook Notification ",
            description = "Receive a webhook notification for a payment transaction",
            tags = { "Webhook", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = WebhookNotification.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(WEBHOOK)
    public ResponseEntity<Void> receiveWebhookNotification(@RequestBody WebhookNotification notification) {
        paymentService.processWebhookNotification(notification);
        return ResponseEntity.noContent().build();
    }


}
