package com.emekauk.paymentgatewaymiddleware.services;

import com.emekauk.paymentgatewaymiddleware.domain.entity.Payment;
import com.emekauk.paymentgatewaymiddleware.domain.mappers.Mapper;
import com.emekauk.paymentgatewaymiddleware.domain.requests.PaymentRequest;
import com.emekauk.paymentgatewaymiddleware.domain.requests.WebhookNotification;
import com.emekauk.paymentgatewaymiddleware.domain.responses.PaymentResponse;
import com.emekauk.paymentgatewaymiddleware.domain.responses.PaymentStatusResponse;
import com.emekauk.paymentgatewaymiddleware.downstreamService.BankServicePaymentResponse;
import com.emekauk.paymentgatewaymiddleware.repositories.PaymentRepository;
import com.emekauk.paymentgatewaymiddleware.utils.ApiUrls;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

import static com.emekauk.paymentgatewaymiddleware.downstreamService.BankServicePaymentResponse.Status.IP;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final Mapper<Payment, PaymentResponse> paymentMapper;

    private final RestTemplate restTemplate;

    @Override
    public PaymentResponse initiatePayment(PaymentRequest paymentRequest) {
        String transactionId = UUID.randomUUID().toString();

        BankServicePaymentResponse response = restTemplate.postForObject(ApiUrls.DOWNSTREAM_PROCESS_PAYMENT, paymentRequest, BankServicePaymentResponse.class);

        if(Objects.nonNull(response)) {
            response.setTransactionId(transactionId);
        }else{
            response = BankServicePaymentResponse.builder()
                    .transactionId(transactionId)
                    .status(IP)
                    .build();
        }

        Payment payment = null;
        try{
             payment = Payment.builder()
                    .amount(paymentRequest.getAmount())
                    .transactionId(transactionId)
                     .currencyCode(paymentRequest.getCurrencyCode())
                     .countryCode(paymentRequest.getCountryCode())
                    .status(getStatusFromPaymentRequest(response))
                    .build();
        }catch (Exception ex){
            log.error(ex.getMessage());
            payment = new Payment();
        }

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.mapTo(savedPayment);
    }

    @Override
    public PaymentStatusResponse checkPaymentStatus(String transactionId) {

        Payment payment = null;
        PaymentStatusResponse paymentStatusResponse = new PaymentStatusResponse();
        try{
            payment = getPaymentByTransactionId(transactionId);
        }catch(Exception ex){
            log.error(ex.getMessage());

        }

        if(payment != null){
            if(payment.getStatus() == Payment.PaymentStatus.INITIATED){
                BankServicePaymentResponse paymentResponse = restTemplate.getForObject(ApiUrls.DOWNSTREAM_RE_QUERY_URL,  BankServicePaymentResponse.class);
                if(paymentResponse != null) {
                    try {
                        Payment.PaymentStatus statusFromPaymentRequest = getStatusFromPaymentRequest(paymentResponse);
                        payment.setStatus(statusFromPaymentRequest);
                        paymentStatusResponse.setStatus(statusFromPaymentRequest);
                        paymentRepository.save(payment);

                    } catch (Exception ex) {
                        log.info(ex.getMessage());
                    }
                }
            }else{
                paymentStatusResponse.setStatus(payment.getStatus());
            }
        }

        return paymentStatusResponse;
    }


    @Override
    public void processWebhookNotification(WebhookNotification notification) {
        try{
            Payment paymentByTransactionId = getPaymentByTransactionId(notification.getTransactionId());
            paymentByTransactionId.setStatus(notification.getStatus());
            paymentRepository.save(paymentByTransactionId);
        }catch (Exception ex){
            log.info(ex.getMessage());
        }

    }


    private Payment.PaymentStatus getStatusFromPaymentRequest(BankServicePaymentResponse request)throws Exception{
        return switch (request.getStatus()) {
            case IP -> Payment.PaymentStatus.INITIATED;
            case PS -> Payment.PaymentStatus.SUCCESSFUL;
            case PF -> Payment.PaymentStatus.FAILED;
            default -> throw new Exception("An error occurred. Invalid Status");
        };
    }


    private Payment getPaymentByTransactionId(String transactionId) throws Exception {
        return paymentRepository.findPaymentByTransactionId(transactionId)
                .orElseThrow(() -> new Exception("Payment with TransactionId " + transactionId + " does not exist."));
    }
}
