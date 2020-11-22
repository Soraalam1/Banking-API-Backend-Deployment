package com.dreamteam.bankingapi.services;

import com.dreamteam.bankingapi.enums.Medium;
import com.dreamteam.bankingapi.enums.Status;
import com.dreamteam.bankingapi.enums.TransactionType;
import com.dreamteam.bankingapi.models.Account;
import com.dreamteam.bankingapi.models.Transaction;
import com.dreamteam.bankingapi.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.dreamteam.bankingapi.services.TimeStampService.createTimeStamp;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountService accountService;

    public List<Transaction> getAllTransactions(){
        List<Transaction> transactionList = new ArrayList<>();
        transactionRepository.findAll().forEach(transactionList::add);
        transactionList.sort(Comparator.comparing(o -> o.getTransactionDate()));
        Collections.reverse(transactionList);
        return transactionList;
    }

    public Optional<Transaction> getTransactionById(Long id){
        return  transactionRepository.findById(id);
    }

    public List<Transaction> getAllDepositsByAccountId(Long accountId) {
        List<Transaction> listOfDeposits = new ArrayList<>();
        transactionRepository.findAllByPayeeAccountId(accountId).forEach(listOfDeposits::add);
        listOfDeposits.sort(Comparator.comparing(o -> o.getTransactionDate()));
        Collections.reverse(listOfDeposits);
        return listOfDeposits;
    }

    public List<Transaction> getAllWithdrawalsByAccountId(Long accountId) {
        List<Transaction> listOfWithdrawals = new ArrayList<>();
        transactionRepository.findAllByPayerAccountId(accountId).forEach(listOfWithdrawals::add);
        listOfWithdrawals.sort(Comparator.comparing(o -> o.getTransactionDate()));
        Collections.reverse(listOfWithdrawals);
        return listOfWithdrawals;
    }

    public List<Transaction> getAllTransactionsByAccountId(Long accountId){
        List<Transaction> transactionList = new ArrayList<>();
        if(!getAllWithdrawalsByAccountId(accountId).isEmpty()) {
            for (Transaction withdrawal : getAllWithdrawalsByAccountId(accountId)) {
                transactionList.add(withdrawal);
            }
        }
        if(!getAllDepositsByAccountId(accountId).isEmpty()){
            for(Transaction deposit: getAllDepositsByAccountId(accountId)){
            transactionList.add(deposit);
            }
        }
        transactionList.sort(Comparator.comparing(o -> o.getTransactionDate()));
        Collections.reverse(transactionList);

        return transactionList;
    }

    public List<Transaction> getAllDeposits() {
        List<Transaction> listOfDeposits = new ArrayList<>();
        transactionRepository.findAllByType(TransactionType.DEPOSIT.getValue()).forEach(listOfDeposits::add);
        listOfDeposits.sort(Comparator.comparing(o -> o.getTransactionDate()));
        Collections.reverse(listOfDeposits);
        return listOfDeposits;
    }

    public List<Transaction> getAllWithdrawals() {
        List<Transaction> listOfWithdrawals = new ArrayList<>();
        transactionRepository.findAllByType(TransactionType.WITHDRAWAL.getValue()).forEach(listOfWithdrawals::add);
        listOfWithdrawals.sort(Comparator.comparing(o -> o.getTransactionDate()));
        Collections.reverse(listOfWithdrawals);
        return listOfWithdrawals;
    }

    public List<Transaction> getAllP2ps() {
        List<Transaction> listOfp2p = new ArrayList<>();
        transactionRepository.findAllByType(TransactionType.P2P.getValue()).forEach(listOfp2p::add);
        listOfp2p.sort(Comparator.comparing(o -> o.getTransactionDate()));
        Collections.reverse(listOfp2p);
        return listOfp2p;
    }

    public Transaction createTransaction (Transaction transaction, Long accountId) {
        if (transaction.getType().equalsIgnoreCase(TransactionType.DEPOSIT.getValue())) {
            transaction.setPayeeAccountId(accountId);
            Account account = accountService.getAccountById(accountId).get();
            if (transaction.getMedium().equalsIgnoreCase(Medium.BALANCE.getValue())) {

                account.setBalance(account.getBalance()+transaction.getAmount());
                accountService.updateAccount(account.getId(), account);
            }
            else if (transaction.getMedium().equalsIgnoreCase(Medium.REWARDS.getValue())) {
                account.setRewards(account.getRewards() + (transaction.getAmount().intValue()));
                accountService.updateAccount(account.getId(), account);
            }
        }
        else if(transaction.getType().equalsIgnoreCase(TransactionType.WITHDRAWAL.getValue())){
            transaction.setPayerAccountId(accountId);
            Account account = accountService.getAccountById(accountId).get();
            if (transaction.getMedium().equalsIgnoreCase(Medium.BALANCE.getValue())) {

                account.setBalance(account.getBalance() - transaction.getAmount());
                accountService.updateAccount(account.getId(), account);
            }
            else if (transaction.getMedium().equalsIgnoreCase(Medium.REWARDS.getValue())) {
                account.setRewards(account.getRewards() - (transaction.getAmount().intValue()));
                accountService.updateAccount(account.getId(), account);
            }
        }
        transaction.setStatus(Status.PENDING.getValue());
        transaction.setTransactionDate(createTimeStamp());
        return transactionRepository.save(transaction);
    }


    public void updateTransaction(Transaction accountTransaction){
        transactionRepository.save(accountTransaction);
    }

    public Transaction createP2Ptransaction (Transaction transaction, Long payeeId, Long payerId){
            Account payeeAccount = accountService.getAccountById(payeeId).get();//using the Id to get the account so we can manipulate the account
            Account payerAccount = accountService.getAccountById(payerId).get();
            if (transaction.getMedium().equalsIgnoreCase(Medium.BALANCE.getValue())) {
                payeeAccount.setBalance(payeeAccount.getBalance()+transaction.getAmount());
                payerAccount.setBalance(payerAccount.getBalance()-transaction.getAmount());
                accountService.updateAccount(payeeId,payeeAccount);
                accountService.updateAccount(payerId,payerAccount);
                transactionRepository.save(transaction);
            }
            else if (transaction.getMedium().equalsIgnoreCase(Medium.REWARDS.getValue())) {
                payeeAccount.setRewards(payeeAccount.getRewards() + (transaction.getAmount().intValue()));
                payerAccount.setRewards(payerAccount.getRewards() - (transaction.getAmount().intValue()));
                accountService.updateAccount(payeeId, payeeAccount);
                accountService.updateAccount(payerId,payerAccount);
            }

        transaction.setStatus(Status.PENDING.getValue());
        transaction.setTransactionDate(createTimeStamp());
        return transactionRepository.save(transaction);
    }

    public Transaction cancelTransaction(Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId).get();
        Long accountId;
        if(!transaction.getStatus().equalsIgnoreCase(Status.CANCELLED.getValue())) {
            if (transaction.getType().equalsIgnoreCase(TransactionType.DEPOSIT.getValue())) {
                accountId = transaction.getPayeeAccountId();
                Account account = accountService.getAccountById(accountId).get();
                if (transaction.getMedium().equalsIgnoreCase(Medium.BALANCE.getValue())) {
                    account.setBalance(account.getBalance() - transaction.getAmount());
                    accountService.updateAccount(accountId, account);
                } else if (transaction.getMedium().equalsIgnoreCase(Medium.REWARDS.getValue())) {
                    account.setRewards(account.getRewards() - (transaction.getAmount().intValue()));
                    accountService.updateAccount(accountId, account);
                }
            } else if (transaction.getType().equalsIgnoreCase(TransactionType.WITHDRAWAL.getValue())) {
                accountId = transaction.getPayerAccountId();
                Account account = accountService.getAccountById(accountId).get();
                if (transaction.getMedium().equalsIgnoreCase(Medium.BALANCE.getValue())) {
                    account.setBalance(account.getBalance() + transaction.getAmount());
                    accountService.updateAccount(accountId, account);
                } else if (transaction.getMedium().equalsIgnoreCase(Medium.REWARDS.getValue())) {
                    account.setRewards(account.getRewards() + (transaction.getAmount().intValue()));
                    accountService.updateAccount(accountId, account);
                }
            } else if (transaction.getType().equalsIgnoreCase(TransactionType.P2P.getValue())) {
                Long accountPayeeId = transaction.getPayeeAccountId();
                Long accountPayerId = transaction.getPayerAccountId();
                Account accountPayee = accountService.getAccountById(accountPayeeId).get();
                Account accountPayer = accountService.getAccountById(accountPayerId).get();
                if (transaction.getMedium().equalsIgnoreCase(Medium.BALANCE.getValue())) {
                    accountPayer.setBalance(accountPayer.getBalance() + transaction.getAmount());
                    accountPayee.setBalance(accountPayee.getBalance() - transaction.getAmount());
                    accountService.updateAccount(accountPayeeId, accountPayee);
                    accountService.updateAccount(accountPayerId, accountPayer);
                } else if (transaction.getMedium().equalsIgnoreCase(Medium.REWARDS.getValue())) {
                    accountPayer.setRewards(accountPayer.getBalance().intValue() + transaction.getAmount().intValue());
                    accountPayee.setRewards(accountPayee.getBalance().intValue() - transaction.getAmount().intValue());
                    accountService.updateAccount(accountPayeeId, accountPayee);
                    accountService.updateAccount(accountPayerId, accountPayer);
                }
            }

            transaction.setStatus(Status.CANCELLED.getValue());

        }
        return transactionRepository.save(transaction);

    }
    public void deleteTransactionById(Long id){

        transactionRepository.deleteById(id);

    }
}
