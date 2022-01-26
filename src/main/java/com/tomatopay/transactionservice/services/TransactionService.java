package com.tomatopay.transactionservice.services;

import com.tomatopay.transactionservice.exceptions.InvalidTransaction;
import com.tomatopay.transactionservice.model.Credit;
import com.tomatopay.transactionservice.model.Debit;
import com.tomatopay.transactionservice.model.Transaction;
import com.tomatopay.transactionservice.model.TransactionType;
import com.tomatopay.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.tomatopay.transactionservice.constants.TransactionConstants.PAGE_LIMIT;
import static com.tomatopay.transactionservice.constants.TransactionConstants.THREAD_LIMIT;

@Service
public class TransactionService {

    final ExecutorService executor = Executors.newFixedThreadPool(THREAD_LIMIT);
    @Autowired
    private TransactionRepository transactionRepository;

    public Iterable<Transaction> getAllTransactions(Integer page, Integer limit) {
        if (limit == null || limit > PAGE_LIMIT)
            limit = PAGE_LIMIT;
        return transactionRepository.findAll(PageRequest.of(page, limit));
    }

    public Transaction createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        executor.execute(() -> {
            this.performBankTransaction(transaction);
        });
        return transaction;
    }

    public Transaction getTransaction(UUID id) throws InvalidTransaction {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent())
            return transaction.get();
        else
            throw new InvalidTransaction("No Transaction is present with id " + id);
    }

    public Transaction updateTransaction(UUID id, Transaction updateTransaction) throws InvalidTransaction {
        if (!transactionRepository.existsById(id))
            throw new InvalidTransaction("No Transaction is present with id " + id);
        updateTransaction.setId(id);
        return transactionRepository.save(updateTransaction);
    }

    public String deleteTransaction(UUID id) {
        try {
            transactionRepository.deleteById(id);
            return "Successfully deleted the transaction " + id;
        } catch (Exception ex) {
            return "Failed to delete the transaction " + id;
        }
    }

    private void performBankTransaction(Transaction transaction) {
        TransactionType transactionType;
        switch (transaction.getType()) {
            case DEBIT:
                transactionType = new Debit(transaction.getAccountId());
                break;
            case CREDIT:
                transactionType = new Credit(transaction.getAccountId());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + transaction.getType());
        }
        synchronized (this) {
            transactionType.execute(transaction.getAmount());
        }
    }
}