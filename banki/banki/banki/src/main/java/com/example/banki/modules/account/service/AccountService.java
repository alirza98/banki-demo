package com.example.banki.modules.account.service;

import com.example.banki.modules.account.model.Account;
import com.example.banki.modules.account.repository.AccountRepository;
import com.example.banki.modules.bankBranch.model.BankBranch;
import com.example.banki.modules.bankBranch.repository.BankBranchRepository;
import com.example.banki.modules.customer.model.Customer;
import com.example.banki.modules.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final BankBranchRepository bankBranchRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository, BankBranchRepository bankBranchRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.bankBranchRepository = bankBranchRepository;
    }


    @Transactional
    public Account save(int cusId ,int bankId){
        Customer customer = customerRepository.findById(cusId).get();
        BankBranch bankBranch = bankBranchRepository.findById(bankId).get();
        Account account = new Account(customer,bankBranch);
        return accountRepository.save(account);
    }


    public Customer transferToTheAccount (int accSource , double amount , int accDestination ){
        Account source = accountRepository.findById(accSource).get();
        Account destination = accountRepository.findById(accDestination).get();
        if(source.getCurrentBalance() > amount){
            double deductionAcc  = source.getCurrentBalance() - amount;
            source.setCurrentBalance(deductionAcc);
            double addAccount = destination.getCurrentBalance() + amount;
            destination.setCurrentBalance(addAccount);
            accountRepository.save(destination);
        }else {
            new RuntimeException("Your account balance is insufficient");
        }
        Account print = accountRepository.findById(accDestination).orElseThrow(()-> new RuntimeException("Your ID is not available"));
        Customer printCus = customerRepository.findById(print.getCustomer().getId()).orElseThrow(()-> new RuntimeException("Your ID is not available"));
        return printCus;
    }

        public Account depositToAccount(int accId , double amount){
        Account a = accountRepository.findById(accId).get();
        if (amount > 0 ){
            a.setCurrentBalance(amount);
            accountRepository.save(a);
        }else {
            new RuntimeException("The amount entered is not correct");
        }
        return  accountRepository.findById(accId).orElseThrow(()-> new RuntimeException("Your ID is not available"));
        }


        public List<Account> getAllAccounts(){
        return accountRepository.findAll();
        }
}
