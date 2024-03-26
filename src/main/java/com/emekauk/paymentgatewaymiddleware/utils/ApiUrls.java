package com.emekauk.paymentgatewaymiddleware.utils;

import org.springframework.stereotype.Component;

@Component
public interface ApiUrls {
     String PAYMENT = "api/v1/payments";
     String INITIATE = "initiate";
     String STATUS = "status/{transactionId}";
     String WEBHOOK = "webhook";

     String DOWNSTREAM_PROCESS_PAYMENT = "https://fc0cab8f-e5d7-4f89-9897-d36d81de1ce7.mock.pstmn.io/initiate";
     String DOWNSTREAM_RE_QUERY_URL = "https://fc0cab8f-e5d7-4f89-9897-d36d81de1ce7.mock.pstmn.io/check-status";


}
