package com.tomatopay.transactionservice.exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String s) {
        super(s);
    }
}
