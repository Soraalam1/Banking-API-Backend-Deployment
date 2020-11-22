package com.dreamteam.bankingapi.controllers;

import com.dreamteam.bankingapi.response.PlainResponse;
import com.dreamteam.bankingapi.response.Response;
import com.dreamteam.bankingapi.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dreamteam.bankingapi.models.Account;
import com.dreamteam.bankingapi.response.DataResponse;
import com.dreamteam.bankingapi.response.exceptions.NotFound;
import com.dreamteam.bankingapi.services.CustomerService;
import org.springframework.web.bind.annotation.*;




@CrossOrigin
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/accounts")
    public ResponseEntity<Response> getAllAccounts() throws NotFound {
        if (accountService.getAllAccounts().isEmpty()) {
            throw new NotFound("Error: could not fetch accounts, please check if any accounts exist.");
        } else {
            logger.info("Success: All accounts fetched");
            return new ResponseEntity<>(new DataResponse(200, "Success", accountService.getAllAccounts()), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/{accountId}")
    public ResponseEntity<Response> getAccountById(@PathVariable Long accountId) throws NotFound {
        if (!accountService.getAccountById(accountId).isPresent()) {
            throw new NotFound("Error: could not fetch account with ID #" + accountId);
        } else {
            logger.info("Success: account fetched with account ID #" + accountId);
            return new ResponseEntity<>(new DataResponse(200, "Success", accountService.getAccountById(accountId)), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/customer/{customerId}")
    public ResponseEntity<?> getAllCustomerAccounts(@PathVariable Long customerId) throws NotFound {
        if (accountService.getAllCustomerAccounts(customerId).isEmpty()) {
            throw new NotFound("Error: could not fetch customer accounts with customer ID #" + customerId);
        } else {
            logger.info("Success: account fetched with customer ID #" + customerId);
            return new ResponseEntity<>(new DataResponse(200, "Success", accountService.getAllCustomerAccounts(customerId)), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/customers/{customerId}/accounts")
    public ResponseEntity<Response> addAccount(@PathVariable Long customerId, @RequestBody Account account) throws NotFound{
        if(!customerService.getCustomerById(customerId).isPresent()){
            throw new NotFound("Error: customer does not exist yet. Cannot create an account.");
        }
        else{
            logger.info("Success: Account created");
            account.setCustomerId(customerId);
            return new ResponseEntity<>(new DataResponse(201, "New Account Created", accountService.addAccount(account)), HttpStatus.CREATED);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/accounts/{accountId}")
    public ResponseEntity<Response> updateAccount(@PathVariable Long accountId, @RequestBody Account account) throws NotFound{
        if(!accountService.getAccountById(accountId).isPresent()){
            throw new NotFound("Error: customer account with ID #" +accountId + " does not exist.");
        }
        else{
            logger.info("Success: account with ID #" + accountId + " updated");
            accountService.updateAccount(accountId, account);
            return new ResponseEntity<>(new PlainResponse(201, "Customer account updated"), HttpStatus.CREATED);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/accounts/{accountId}")
    public ResponseEntity<Response> deleteAccount(@PathVariable Long accountId) throws NotFound{
        if(!accountService.getAccountById(accountId).isPresent()){
            throw new NotFound("Error: customer account with ID #" + accountId +" does not exist.");
        }
        else{
            accountService.deleteAccount(accountId);
            logger.info("Success: account with ID #" + accountId + " deleted");
            return new ResponseEntity<>(new PlainResponse(200, "Account successfully deleted"), HttpStatus.OK);
        }
    }
}
