package com.tomatopay.transactionservice.model;

import com.tomatopay.transactionservice.exceptions.InvalidAmountException;

import java.util.UUID;

public class Credit implements TransactionType {
    private final UUID accountId;

    public Credit(UUID accountId) {
        this.accountId = accountId;
    }

    public Double execute(Double amount) {
        if (amount <= 0)
            throw new InvalidAmountException("Entered amount should be greater than or equals to ZERO");
        Double balance = Bank.get(accountId);
        return Bank.update(accountId, balance + amount);
    }
}
