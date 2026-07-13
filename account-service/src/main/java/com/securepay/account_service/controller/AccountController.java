package com.securepay.account_service.controller;

import com.securepay.account_service.dto.ApiResponseDto;
import com.securepay.account_service.entity.Account;
import com.securepay.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<ApiResponseDto<Account>> withdraw(@PathVariable String id, @RequestBody Double amount) {
        try {
            Account account = accountService.withdrawal(id, amount);
            ApiResponseDto<Account> response = new ApiResponseDto<>(
                    200,
                    "Withdrawal Successfull",
                    account
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponseDto<Account> response = new ApiResponseDto<>(
                    401,
                    e.getMessage(),
                    null
            );
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>(
                                    500,
                                    e.getMessage(),
                                    null
                            )
                    );
        }
    }

    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable String id, @RequestBody Double amount) {
        return accountService.deposit(id, amount);
    }

    @PostMapping("/create")
    public Account create(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }
}
