package com.dreamteam.bankingapi.controllers;

import com.dreamteam.bankingapi.models.Bill;
import com.dreamteam.bankingapi.response.DataResponse;
import com.dreamteam.bankingapi.response.PlainResponse;
import com.dreamteam.bankingapi.response.Response;
import com.dreamteam.bankingapi.response.exceptions.NotFound;
import com.dreamteam.bankingapi.services.AccountService;
import com.dreamteam.bankingapi.services.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://wutang-bank.herokuapp.com")
@RestController
public class BillController {

    @Autowired
    BillService billService;

    @Autowired
    AccountService accountService;

    Logger logger = LoggerFactory.getLogger(BillController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/bills")
    public ResponseEntity<Response> getAllBills() {
        if (billService.getAllBills().isEmpty()) {
            throw new NotFound("Error: could not fetch bills, please check if any bills exist.");
        } else {
            logger.info("Success: All bills fetched");
            return new ResponseEntity<>(new DataResponse(200, "Success", billService.getAllBills()), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bills/{billId}")
    public ResponseEntity<Response> getBillById(@PathVariable Long billId) throws NotFound {

        if (!billService.getBillById(billId).isPresent()) {
            throw new NotFound("Error: could not fetch any bills with ID #" + billId);
        } else {
            logger.info("Success: Bills found with bill ID #" + billId);
            return new ResponseEntity<>(new DataResponse(200, "Success", billService.getBillById(billId)), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/customers/{customerId}/bills")
    public ResponseEntity<Response> getAllBillsByCustomer(@PathVariable Long customerId) throws NotFound {

        if (billService.getCustomerBills(customerId).isEmpty()) {
            throw new NotFound("Error: could not fetch any bills with customer ID #" + customerId);
        } else {
            logger.info("Success: All bills fetched for customer ID #" + customerId);
            return new ResponseEntity<>(new DataResponse(200, "Success", billService.getCustomerBills(customerId)), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/{accountId}/bills")
    public ResponseEntity<Response> getAllBillsByAccount(@PathVariable Long accountId) throws NotFound {

        if (billService.getAllBillsByAccountId(accountId).isEmpty()) {
            throw new NotFound("Error: could not fetch any bills with account ID #" + accountId);
        } else {
            logger.info("Success: All bills fetched with account ID #" + accountId);
            return new ResponseEntity<>(new DataResponse(200, "Success", billService.getAllBillsByAccountId(accountId)), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accounts/{accountId}/bills")
    public ResponseEntity<Response> createBill(@PathVariable Long accountId, @RequestBody Bill bill) throws NotFound {

        if (!accountService.getAccountById(accountId).isPresent()) {
            throw new NotFound("Error: Account does not exist yet. Cannot create a bill");
        } else {
            bill.setAccountId(accountId);
            bill.setStatus("recurring");
            bill.setPaymentDate("Not Paid Yet");
            logger.info("Success: Bill created with account ID #" + accountId);
            return new ResponseEntity<>(new DataResponse(201, "Bill Created", billService.addBill(bill)), HttpStatus.CREATED);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/bills/{billId}")
    public ResponseEntity<Response> updateBill(@PathVariable Long billId, @RequestBody Bill bill) throws NotFound {
        if (!billService.getBillById(billId).isPresent()) {
            throw new NotFound("Error: Bill with ID #" + billId + " does not exist.");
        } else {
            logger.info("Success: Bill with ID #" + billId + " updated");
            billService.updateBill(billId, bill);
            return new ResponseEntity<>(new PlainResponse(201, "Bill updated"), HttpStatus.CREATED);
        }
    }

    //
    @RequestMapping(method = RequestMethod.DELETE, value = "/bills/cancel/{billId}")
    public ResponseEntity<Response> cancelBill(@PathVariable Long billId) {
        if (!billService.getBillById(billId).isPresent()) {
            throw new NotFound("Error: Bill with ID #" + billId + "does not exist.");
        } else {
            logger.info("Success: Bill with ID #" + billId + " cancelled");
            billService.cancelBill(billId);
            return new ResponseEntity<>(new PlainResponse(201, "Bill canceled"), HttpStatus.CREATED);
        }
    }
}

