package com.securepay.account_service.service.serviceimpl;

import com.securepay.account_service.entity.Account;
import com.securepay.account_service.repository.AccountRepository;
import com.securepay.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account deposit(String id, Double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException());
        account.setBalance(account.getAmount() + amount);
        return accountRepository.save(account);
    }

    @Override
    public Account withdrawal(String id, Double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException());
        if (amount > account.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getAmount() - amount);
        return accountRepository.save(account);
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
}
