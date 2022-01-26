package com.tomatopay.transactionservice.model;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {

    private static final Map<UUID, Double> balance = new ConcurrentHashMap<>();

    public static Double get(UUID id) {
        return balance.getOrDefault(id, 0.00);
    }

    public static Double update(UUID id, Double amount) {
        return balance.put(id, amount);
    }
}
