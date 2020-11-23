package com.dreamteam.bankingapi.controllers;

import com.dreamteam.bankingapi.models.Transaction;
import com.dreamteam.bankingapi.response.PlainResponse;
import com.dreamteam.bankingapi.response.Response;
import com.dreamteam.bankingapi.response.DataResponse;
import com.dreamteam.bankingapi.response.exceptions.NotFound;
import com.dreamteam.bankingapi.services.AccountService;
import com.dreamteam.bankingapi.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ServletResponseMethodArgumentResolver;


@CrossOrigin(origins = "http://wutang-bank.herokuapp.com")
@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/transactions")
    public ResponseEntity<Response> getAllTransaction() throws NotFound {
        if (transactionService.getAllTransactions().isEmpty()){
            throw new NotFound("Error: could not fetch transactions, please check if any transactions exist." );
        }
        else{
            logger.info("Success: All transactions fetched");
            return new ResponseEntity<>(new DataResponse(
                    200, "Success", transactionService.getAllTransactions()),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/transactions/{accountId}")
    public ResponseEntity<Response> createTransaction(@PathVariable Long accountId, @RequestBody Transaction transfer) throws NotFound {
        if (!accountService.getAccountById(accountId).isPresent()){
            throw new NotFound("Error: Transaction with id: " + accountId + " not found." );
        }
        else{
            logger.info("Success: Transaction completed with account ID #" + accountId);
            return new ResponseEntity<>(new DataResponse(
                    201, "Created transaction from account", transactionService.createTransaction(transfer,accountId)),
                    HttpStatus.CREATED);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/transactions/{transactionId}")
    public ResponseEntity<Response> cancelTransaction(@PathVariable Long transactionId) throws NotFound{
        if (!transactionService.getTransactionById(transactionId).isPresent()){
            throw new NotFound("No transactions is found with id: " + transactionId + "." );
        }
        else{
            logger.info("Success: transaction ID #" + transactionId + "has been cancelled");
            return new ResponseEntity<>(new DataResponse(
                    200, "Transaction cancelled", transactionService.cancelTransaction(transactionId)),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transactions/{transactionId}")
    public ResponseEntity<Response> getTransactionById(@PathVariable Long transactionId) throws NotFound{
        if (!transactionService.getTransactionById(transactionId).isPresent()){
            throw new NotFound("No transactions is found with id: " + transactionId + "." );
        }
        else{
            logger.info("Success: transaction with ID #" + transactionId + "fetched");
            return new ResponseEntity<>(new DataResponse(
                    200, "Success", transactionService.getTransactionById(transactionId)),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transactions/deposits")
    public ResponseEntity<Response> getAllDeposits() throws NotFound {
        if (transactionService.getAllDeposits().isEmpty()){
            throw new NotFound("Error: could not fetch deposits, please check if any deposits exist.");
        }
        else{
            logger.info("Success: All deposits fetched");
            return new ResponseEntity<>(new DataResponse(
                    200, "Success", transactionService.getAllDeposits()),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transactions/withdrawals")
    public ResponseEntity<Response> getAllWithdrawals() throws NotFound {
        if (transactionService.getAllWithdrawals().isEmpty()){
            throw new NotFound("Error: could not fetch withdrawals, please check if any withdrawals exist.");
        }
        else{
            logger.info("Success: All withdrawals fetched");
            return new ResponseEntity<>(new DataResponse(
                    200, "Success", transactionService.getAllWithdrawals()),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transactions/transfers")
    public ResponseEntity<Response> getAllP2ps() throws NotFound {
        if (transactionService.getAllP2ps().isEmpty()){
            throw new NotFound("Error: could not fetch P2Ps, please check if any P2Ps exist." );
        }
        else{
            logger.info("Success: All P2Ps fetched");
            return new ResponseEntity<>(new DataResponse(
                    200, "Success", transactionService.getAllP2ps()),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transactions/accounts/{accountId}")
    public ResponseEntity<Response> getAllTransactionByAccountId(@PathVariable Long accountId){
        if (transactionService.getAllTransactionsByAccountId(accountId).isEmpty()){
            throw new NotFound("Error: no transactions were found with account ID #" + accountId);
        }
        else{
            logger.info("Success: All transactions with account ID #" + accountId + "found");
            return new ResponseEntity<>(new DataResponse(
                    200, "Success", transactionService.getAllTransactionsByAccountId(accountId)),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transactions/accounts/{accountId}/deposits")
    public ResponseEntity<Response> getAllDepositsByAccountId(@PathVariable Long accountId) {
        if (transactionService.getAllDepositsByAccountId(accountId).isEmpty()){
            throw new NotFound("Error: no deposits were found with account ID #" + accountId);
        } else {
            logger.info("Success: All deposits with account ID #" + accountId + "found");
            return new ResponseEntity<>(new DataResponse(
                    200,"Success", transactionService.getAllDepositsByAccountId(accountId)),HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transactions/accounts/{accountId}/withdrawals")
    public ResponseEntity<Response> getAllWithdrawalsByAccountId(@PathVariable Long accountId) throws NotFound {
        if (transactionService.getAllWithdrawalsByAccountId(accountId).isEmpty()){
            throw new NotFound("Error: no withdrawals were found with account ID #" + accountId);
        }
        else{
            logger.info("Success: All withdrawals with account ID #" + accountId + "found");
            return new ResponseEntity<>(new DataResponse(
                    200, "Success", transactionService.getAllWithdrawalsByAccountId(accountId)),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/transactions/accounts/{payerId}/transfersto/{payeeId}")
    public ResponseEntity<Response> createTransfer(@PathVariable Long payerId, @PathVariable Long payeeId, @RequestBody Transaction transfersto){
        if (!accountService.getAccountById(payerId).isPresent() || !accountService.getAccountById(payeeId).isPresent()){
            throw new NotFound("Error: could not create transfer between these 2 accounts");
        }
        else{
            logger.info("Success: transfer created between these 2 accounts");
            return new ResponseEntity<>(new DataResponse(
                    201, "Created transfer", transactionService.createP2Ptransaction(transfersto,payeeId,payerId)),
                    HttpStatus.CREATED);
        }
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/transactions/{transactionId}/delete")
    public ResponseEntity<Response> deleteTransactionById(@PathVariable Long transactionId){
        if(!transactionService.getTransactionById(transactionId).isPresent()){
            throw new NotFound("Error: transaction with ID #" + transactionId + " does not exist.");
        } else{
            transactionService.deleteTransactionById(transactionId);
            return new ResponseEntity<>(new PlainResponse(200, "Deleted Transaction"), HttpStatus.OK);
        }
    }

}



