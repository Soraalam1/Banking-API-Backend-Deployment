package com.dreamteam.bankingapi;

import com.dreamteam.bankingapi.models.Account;
import com.dreamteam.bankingapi.models.Customer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//THIS IS OUR DUMMY TEST
public class TestUtils {

    public static List<Customer> getTestCustomers() { //list of customers

        return Stream.of(makeCustomer()).collect(Collectors.toList()); //Return Value: Stream of(T t) returns a sequential Stream containing the single specified element.
    }


    public static Optional<Account> getAccount(){
        Account account = new Account();
        account.setBalance(1000000000000.00);
        account.setCustomerId(makeCustomer().getId());
        account.setId(1L);
        account.setNickname("Vanilla Ice");
        account.setRewards(1000000000);
        account.setType("Checking");
        return Optional.of(account);
    }

    private static Customer makeCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Ryan");
        customer.setLastName("Batchelor");
        customer.setId(1L);

        return customer;
    }
    private static Customer UpdateByCustomerId(Long id, Customer customer){
        customer.setId(1L);
        return customer;


    }
    public static Customer getTestCustomer(){

        return makeCustomer();
    }
}
