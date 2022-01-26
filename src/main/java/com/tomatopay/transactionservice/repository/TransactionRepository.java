package com.tomatopay.transactionservice.repository;

import com.tomatopay.transactionservice.model.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, UUID> {

}