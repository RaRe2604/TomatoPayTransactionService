package com.tomatopay.transactionservice.model;

import com.tomatopay.transactionservice.exceptions.InvalidAmountException;

import java.util.UUID;

public class Debit implements TransactionType {
    private final UUID accountId;

    public Debit(UUID accountId) {
        this.accountId = accountId;
    }

    public Double execute(Double amount) {
        Double balance = Bank.get(accountId);
        if (amount <= 0)
            throw new InvalidAmountException("Entered amount should be greater than or equals to ZERO");
        if (balance < amount)
            throw new InvalidAmountException("Cannot debit. Requested amount exceeds the balance amount.");
        return Bank.update(accountId, balance - amount);
    }
}
