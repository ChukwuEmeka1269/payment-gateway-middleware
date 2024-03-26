package com.emekauk.paymentgatewaymiddleware.domain.mappers;

public interface Mapper<A,B> {

    B mapTo(A a);

    A mapFrom(B b);
}
