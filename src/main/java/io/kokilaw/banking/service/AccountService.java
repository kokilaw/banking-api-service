package io.kokilaw.banking.service;

import io.kokilaw.banking.dto.AccountDTO;

/**
 * Created by kokilaw on 2022-08-09
 */
public interface AccountService {

    AccountDTO createAccount(AccountDTO account);

    AccountDTO getAccount(long accountId);

    AccountDTO updateAccount(long accountId, AccountDTO account);

    boolean addToAccountBalance(long accountId, long amount);

    void deleteAccount(long accountId);

}
