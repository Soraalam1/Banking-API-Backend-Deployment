package com.dreamteam.bankingapi.controllers;

import com.dreamteam.bankingapi.models.Customer;
import com.dreamteam.bankingapi.response.DataResponse;
import com.dreamteam.bankingapi.response.Response;
import com.dreamteam.bankingapi.response.exceptions.NotFound;
import com.dreamteam.bankingapi.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    Logger logger = LoggerFactory.getLogger(CustomerController.class);


    @RequestMapping(method = RequestMethod.GET, value = "/customers")
    public ResponseEntity<Response> getAllCustomers() throws NotFound {
        if (customerService.getAllCustomers().isEmpty()) {
            throw new NotFound("Error: could not fetch customers, please check if any customers exist.");

        }
        else{
            logger.info("Success: All customers fetched");
            return new ResponseEntity<>(new DataResponse(200, "Success", customerService.getAllCustomers()), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/customers")
    public ResponseEntity<Response> addCustomer(@RequestBody Customer customer){
        logger.info("Success: Customer created");
            return new ResponseEntity<>(new DataResponse(201, "Customer account created", customerService.addCustomer(customer)), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/customers/{id}")
    public ResponseEntity<Response> updateCustomerById(@PathVariable Long id, @RequestBody Customer customer) throws NotFound {
        if (!customerService.getCustomerById(id).isPresent()) {
            throw new NotFound("Error: could not fetch customer with ID #" + id);
        }
        else{
            logger.info("Success: customer with ID #" + id + " updated");
            return new ResponseEntity<>(new DataResponse(201, "Customer account updated", customerService.updateCustomerById(id, customer)), HttpStatus.CREATED); 
          //201 = the request has succeeded and has led to the creation of a resource
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/{accountId}/customer")
    public ResponseEntity<Response> getCustomerAccountById(@PathVariable Long accountId) throws NotFound {
        if (!customerService.getCustomerByAccountId(accountId).isPresent()) {
            throw new NotFound("Error: customer not found with account ID #" + accountId);

        }
        else{
            logger.info("Success: customer with account ID #" + accountId + " found");
            return new ResponseEntity<>(new DataResponse(200, "Success", customerService.getCustomerByAccountId(accountId)), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/customers/{id}")
    public ResponseEntity<Response> getCustomerById(@PathVariable Long id) throws NotFound {
        if (!customerService.getCustomerById(id).isPresent()) {
            throw new NotFound("Error: customer not found with ID #" + id);
        } else {
            logger.info("Success: customer with ID #" + id + " found");
            return new ResponseEntity<>(new DataResponse(200, "Success", customerService.getCustomerById(id)), HttpStatus.OK);
        }

    }
}
