package io.kokilaw.banking.service;

import io.kokilaw.banking.dto.AccountDTO;

import java.math.BigDecimal;

/**
 * Created by kokilaw on 2022-08-09
 */
public interface AccountService {

    AccountDTO createAccount(AccountDTO account);

    AccountDTO getAccount(Long accountId);

    AccountDTO updateAccount(Long accountId, AccountDTO account);

    AccountDTO updateAccountBalance(Long accountId, BigDecimal balance);

    void deleteAccount(Long accountId);

}
