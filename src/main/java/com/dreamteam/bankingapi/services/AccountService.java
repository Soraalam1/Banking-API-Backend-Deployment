package com.dreamteam.bankingapi.services;

import com.dreamteam.bankingapi.enums.TransactionType;
import com.dreamteam.bankingapi.models.Account;
import com.dreamteam.bankingapi.models.Bill;
import com.dreamteam.bankingapi.models.Customer;
import com.dreamteam.bankingapi.models.Transaction;
import com.dreamteam.bankingapi.repositories.AccountRepository;
import com.dreamteam.bankingapi.repositories.BillRepository;
import com.dreamteam.bankingapi.repositories.CustomerRepository;

import com.dreamteam.bankingapi.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;


@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TransactionService transactionService;
    @Autowired
    BillService billService;



    public List<Account> getAllAccounts(){
        List<Account> accountList = new ArrayList<>();
        accountRepository.findAll().forEach(accountList::add);
        return accountList;

    }

    public Optional<Account> getAccountById(Long id){
        return accountRepository.findById(id);
    }

    public List<Account> getAllCustomerAccounts(Long customerId){
        List<Account> accountList = new ArrayList<>();
        accountRepository.findAllByCustomerId(customerId).forEach(accountList::add);
        return accountList;
    }

    public Account addAccount(Account account){
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account account){
        account.setId(id);
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id){
        for(Transaction transaction: transactionService.getAllTransactionsByAccountId(id)){
            if (transaction.getType().equalsIgnoreCase(TransactionType.DEPOSIT.getValue()) || transaction.getType().equalsIgnoreCase(TransactionType.WITHDRAWAL.getValue())){
                transactionService.deleteTransactionById(transaction.getId());
            }
        }
        for (Bill bill: billService.getAllBillsByAccountId(id)) {
            billService.deleteBill(bill.getId());
        }

        accountRepository.deleteById(id);
    }
}
