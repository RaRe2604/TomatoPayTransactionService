package com.tomatopay.transactionservice.controllers;

import com.tomatopay.transactionservice.model.Transaction;
import com.tomatopay.transactionservice.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity getAllTransactions(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false) Integer limit) {
        return new ResponseEntity(transactionService.getAllTransactions(page, limit), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(transactionService.getTransaction(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable("id") UUID id, @RequestBody Transaction updateTransaction) {
        return new ResponseEntity<>(transactionService.updateTransaction(id, updateTransaction), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(transactionService.deleteTransaction(id), HttpStatus.OK);
    }
}