package com.tomatopay.transactionservice.exceptions;

public class InvalidTransaction extends RuntimeException {
    public InvalidTransaction(String s) {
        super(s);
    }
}
