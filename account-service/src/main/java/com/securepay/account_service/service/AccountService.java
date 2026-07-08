package com.securepay.account_service.service;

import com.securepay.account_service.entity.Account;
import com.securepay.account_service.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface AccountService {

     Account deposit(String id, Double amount);

     Account withdrawal(String id, Double amount);

     Account createAccount(Account account);
}
