package com.dreamteam.bankingapi.repositories;

import com.dreamteam.bankingapi.models.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Iterable<Transaction> findAllByPayeeAccountId(Long id);
    Iterable<Transaction> findAllByPayerAccountId(Long id);
    Transaction findByPayeeAccountId(Long id);
    Iterable<Transaction> findAllByType(String type);




}
