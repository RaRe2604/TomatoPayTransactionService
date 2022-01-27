package com.tomatopay.transactionservice;

import com.tomatopay.transactionservice.constants.TransactionType;
import com.tomatopay.transactionservice.model.Transaction;
import com.tomatopay.transactionservice.services.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
class TransactionserviceApplicationTests {

    @Autowired
    TransactionService transactionService;

    static UUID[] accountIds;
    static String[] currencies;

    @BeforeAll
    static void setup() {
        accountIds = new UUID[]{
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()
        };
        currencies = new String[]{
                "INR", "USD", "AUS", "AED", "THB"
        };
    }

    @Test
    void allTransactionsWithData() {
        for (int i = 0; i < 10; i++) {
            int index = new Random().nextInt(5);
            Transaction transaction = new Transaction();
            transaction.setId(UUID.randomUUID());
            transaction.setAccountId(accountIds[index]);
            transaction.setCurrency(currencies[index]);
            transaction.setAmount(new Random().nextDouble() + 100);
            transaction.setDescription("Added");
            transaction.setType(TransactionType.CREDIT);
            transactionService.createTransaction(transaction);
        }
        int limit = 2;
        Iterable<Transaction> allTransactions = transactionService.getAllTransactions(0, limit);
        Iterator<Transaction> iterator = allTransactions.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        Assertions.assertEquals(limit, count);
    }

    @Test
    void getTransactionsWithData() {
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountIds[1]);
        transaction.setCurrency(currencies[1]);
        transaction.setAmount(new Random().nextDouble() + 100);
        transaction.setDescription("Added");
        transaction.setType(TransactionType.CREDIT);
        Transaction dbTransaction = transactionService.createTransaction(transaction);

        Assertions.assertEquals(currencies[1], dbTransaction.getCurrency());
        Assertions.assertEquals(accountIds[1], dbTransaction.getAccountId());
    }

    @Test
    void updateTransactions() {
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountIds[1]);
        transaction.setCurrency(currencies[1]);
        transaction.setAmount(new Random().nextDouble() + 100);
        transaction.setDescription("Added");
        transaction.setType(TransactionType.CREDIT);
        transaction = transactionService.createTransaction(transaction);

        UUID id = transaction.getId();

        Transaction dbTransaction = transactionService.getTransaction(id);
        Assertions.assertEquals("Added", dbTransaction.getDescription());

        transaction.setDescription("Updated");
        transactionService.updateTransaction(id, transaction);

        dbTransaction = transactionService.getTransaction(id);
        Assertions.assertEquals("Updated", dbTransaction.getDescription());
    }

    @Test
    void deleteTransactionFailed() {
        UUID uuid = UUID.randomUUID();
        String result = transactionService.deleteTransaction(uuid);
        Assertions.assertEquals("Failed to delete the transaction " + uuid, result);
    }

}
