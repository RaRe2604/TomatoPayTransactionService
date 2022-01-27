package com.tomatopay.transactionservice.controllers;

import com.tomatopay.transactionservice.model.Bank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("balance")
public class BalanceController {

    @GetMapping("/{id}")
    public Double getBalance(@PathVariable UUID id) {
        return Bank.get(id);
    }
}
