package io.kokilaw.banking.service;

import io.kokilaw.banking.dto.AccountDTO;

/**
 * Created by kokilaw on 2022-08-09
 */
public interface AccountService {

    AccountDTO createAccount(AccountDTO account);

    AccountDTO getAccount(Long accountId);

    AccountDTO updateAccount(Long accountId, AccountDTO account);

    void deleteAccount(Long accountId);

}
