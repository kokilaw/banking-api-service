package io.kokilaw.banking.service;

import io.kokilaw.banking.dto.AccountDTO;
import io.kokilaw.banking.error.exception.NotFoundException;

/**
 * Created by kokilaw on 2022-08-09
 */
public interface AccountService {

    AccountDTO createAccount(AccountDTO account);

    AccountDTO getAccount(long accountId);

    AccountDTO updateAccount(long accountId, AccountDTO account);

    AccountDTO updateAccountBalance(long accountId, long balance);

    void deleteAccount(long accountId);

}
