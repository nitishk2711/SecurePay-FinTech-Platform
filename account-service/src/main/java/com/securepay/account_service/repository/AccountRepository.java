package com.securepay.account_service.repository;

import com.securepay.account_service.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account,String> {
}
