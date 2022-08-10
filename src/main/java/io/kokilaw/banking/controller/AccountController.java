package io.kokilaw.banking.controller;

import io.kokilaw.banking.dto.AccountDTO;
import io.kokilaw.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by kokilaw on 2022-08-09
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public AccountDTO createAccount(@Valid @RequestBody AccountDTO account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/{accountId}")
    public AccountDTO getAccount(@PathVariable Long accountId) {
        return accountService.getAccount(accountId);
    }

    @PutMapping("/{accountId}")
    public AccountDTO updateAccount(@PathVariable Long accountId, @Valid @RequestBody AccountDTO account) {
        return accountService.updateAccount(accountId, account);
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
    }


}
