package com.dreamteam.bankingapi.servicestest;

import com.dreamteam.bankingapi.models.Customer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtils {

    public static List<Customer> getTestCustomers() {
        return Stream.of(makeCustomer()).collect(Collectors.toList());
    }

    private static Customer makeCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Ray");
        customer.setLastName("Esco");
        customer.setId(1L);

        return customer;
    }
}
