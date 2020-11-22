package com.dreamteam.bankingapi.services;

import com.dreamteam.bankingapi.models.Customer;
import com.dreamteam.bankingapi.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    //this connects back to the Crud

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountService accountService;


    public Optional<Customer> getCustomerById(Long id){
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customerList = new ArrayList<>();
        customerRepository.findAll().forEach(customerList::add);
        return customerList;
    }


    public Customer addCustomer(Customer customer){
        return customerRepository.save(customer);
    }


    public Customer updateCustomerById(Long id, Customer customer){
        customer.setId(id);
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerByAccountId(Long accountID) {
       Long customerID = accountService.getAccountById(accountID).get().getCustomerId();
       return getCustomerById(customerID);
    }
    

}
