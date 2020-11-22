package com.dreamteam.bankingapi.repositories;

import com.dreamteam.bankingapi.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200")
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findCustomerByFirstName(String firstName);

    Optional<Customer> findCustomerByLastName(String lastName);

}
