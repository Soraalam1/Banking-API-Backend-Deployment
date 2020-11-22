package com.dreamteam.bankingapi.repositories;

import com.dreamteam.bankingapi.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Iterable<Account> findAllByCustomerId(Long customerId);
}
