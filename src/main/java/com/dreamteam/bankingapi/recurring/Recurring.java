package com.dreamteam.bankingapi.recurring;

import com.dreamteam.bankingapi.enums.Medium;
import com.dreamteam.bankingapi.enums.TransactionType;
import com.dreamteam.bankingapi.models.Transaction;
import com.dreamteam.bankingapi.enums.Status;
import com.dreamteam.bankingapi.models.Account;
import com.dreamteam.bankingapi.models.Bill;
import com.dreamteam.bankingapi.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Recurring {

    //Corey and Darren

    // get all bills
    // get the upcomingPaymentDate from the bill
    // get today's date - Date
    // if upcomingPaymentDate == todaysDate notify the customer
    // schedule method to be invoked every 10 minutes
    @Autowired
    BillService billService;

    @Autowired
    CustomerService customerService;

    @Autowired
    AccountService accountService;


    @Scheduled (fixedDelay = 10000)
    public void autoUpdateBills(){
        String todaysDate = TimeStampService.createTimeStamp();
        // Get all bills
        List<Bill> allBills = billService.getAllBills();
        List<Bill> billsDueToday = new ArrayList<>();
        for (Bill bill: allBills) {
            if(bill.getUpcomingPaymentDate().equals(todaysDate)){
                billsDueToday.add(bill);
            }
        }
        // Get all accounts
        List<Account> allAccounts = accountService.getAllAccounts();
        // Loop through all accounts
        for (Account account: allAccounts) {
            // Grab the account ID
            Long accountId = account.getId();
            // Loop through all bills due
            for (Bill billDue: billsDueToday) {
                // Match bill to correct account using ID
                if(billDue.getAccountId().equals(accountId)){
                    // if the account has enough money then automagically pay the bill
                    if(account.getBalance() >= billDue.getPaymentAmount()){
                        // Subtract the payment amount
                        Transaction transaction = new Transaction(TransactionType.WITHDRAWAL.getValue(), account.getId(), Medium.BALANCE.getValue(), billDue.getPaymentAmount(), "Automatic monthly payment to " + billDue.getPayee() + ".");
                        account.setBalance((account.getBalance() - billDue.getPaymentAmount()));
                        transactionService.createTransaction(transaction, account.getId());
                        logger.info("Recurring bill with the id # " + billDue.getId() + " has been posted.");
                        billDue.setUpcomingPaymentDate(TimeStampService.createUpcomingDate(billDue));
                        billDue.setPaymentDate(TimeStampService.createTimeStamp());
                        billService.updateBill(billDue.getId(), billDue);
                    }
                    // if they dont have enough money set status to cancelled instead of recurring
                    else{
                        billDue.setStatus(Status.CANCELLED.getValue());
                        logger.info("Recurring bill with the id # " + billDue.getId() + " could not be paid.");
                    }
                }
            }
        }

    }


    /* Yas and Jay
1. Get all pending transactions
2. Change status from pending to completed for each one in the list
3. Has to give results every 3 minutes
4. Use an IF statement
5. Use the enums.value() method because it will be returning an array of all enum constants
    * */

    @Autowired
    TransactionService transactionService;

    Logger logger = LoggerFactory.getLogger(Recurring.class);

    @Scheduled(fixedDelay = 10000)
    public void deleteCancelledTransactions(){
        List<Transaction> transactions = transactionService.getAllTransactions();
        for(Transaction transaction : transactions) {
            if(transaction.getStatus().equals(Status.CANCELLED.getValue())) {
                transactionService.deleteTransactionById(transaction.getId());
                logger.info("transaction ID #" + transaction.getId() + " has been deleted");
            }
        }
    }

    @Scheduled(fixedDelay = 20000)
    public void deleteCancelledBills(){
        List<Bill> bills = billService.getAllBills();
        for(Bill bill : bills) {
            if(bill.getStatus().equals(Status.CANCELLED.getValue())) {
                billService.deleteBill(bill.getId());
                logger.info("Bill ID #" + bill.getId() + " has been deleted");
            }
        }
    }


    @Scheduled(fixedDelay = 10000)
    public void pendingToApproved(){
    List<Transaction> transactions = transactionService.getAllTransactions(); //Getting the list of the transaction
    for(Transaction transaction : transactions){ //For each transaction we are looping through
        if(transaction.getStatus().equals(Status.PENDING.getValue())) { //If the status of the transaction equals PENDING, then get the value
            transaction.setStatus(Status.COMPLETED.getValue()); //Once we get the value, we set the status to COMPLETED
            transactionService.updateTransaction(transaction); //Updating the transaction every 10 seconds
            logger.info("Transaction ID #" + transaction.getId() + " went from pending to approved"); //logging in the updated information. Pending to approved!
            }
        }
    }
}
