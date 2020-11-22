package com.dreamteam.bankingapi.services;

import com.dreamteam.bankingapi.enums.Status;
import com.dreamteam.bankingapi.models.Account;
import com.dreamteam.bankingapi.models.Bill;
import com.dreamteam.bankingapi.models.Customer;
import com.dreamteam.bankingapi.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {


    @Autowired
    BillRepository billRepository;

    @Autowired
    AccountService accountService;




    public List<Bill> getAllBills(){
        List<Bill> bills = new ArrayList<>();
        billRepository.findAll().forEach(bills::add);
        return bills;
    }

    public List<Bill> getAllBillsByAccountId(Long accountId) {
        List<Bill> bills = new ArrayList<>();
        billRepository.findAllByAccountId(accountId).forEach(bills::add);
        return bills;
    }

    public Optional<Bill> getBillById(Long id) {
        return billRepository.findById(id);

    }

    public List<Bill> getCustomerBills(Long customerId) {
        List<Bill> customerBills = new ArrayList<>();

        List<Account> accounts = accountService.getAllCustomerAccounts(customerId);
        for (Account account: accounts) {

            List<Bill> accountBills = getAllBillsByAccountId(account.getId());
            for (Bill bill:accountBills) {
                customerBills.add(bill);
            }
        }
        return customerBills;
    }


    public Bill addBill(Bill bill) {
        bill.setCreationDate(TimeStampService.createTimeStamp());
        TimeStampService.initializeUpcomingDate(bill);
        return billRepository.save(bill);
    }

        public Bill updateBill (Long id, Bill bill){
            bill.setId(id);
            bill.setCreationDate(TimeStampService.createTimeStamp());
            return billRepository.save(bill);
        }


        public void deleteBill (Long id){
            billRepository.deleteById(id);
        }

        public Bill cancelBill (Long id){
            Bill bill = getBillById(id).get();
            bill.setStatus(Status.CANCELLED.getValue());
            return billRepository.save(bill);
        }

}
