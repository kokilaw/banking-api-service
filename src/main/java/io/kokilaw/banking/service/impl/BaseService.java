package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.exception.AccountNotFoundException;
import io.kokilaw.banking.exception.CurrencyNotSupportedException;
import io.kokilaw.banking.repository.AccountRepository;
import io.kokilaw.banking.repository.CurrencyRepository;
import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.Currency;

/**
 * Created by kokilaw on 2022-08-11
 */
abstract public class BaseService {

    protected final AccountRepository accountRepository;
    protected final CurrencyRepository currencyRepository;


    public BaseService(AccountRepository accountRepository, CurrencyRepository currencyRepository) {
        this.accountRepository = accountRepository;
        this.currencyRepository = currencyRepository;
    }

    protected Account getAccountIfAvailable(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException(String.format("Account not found with accountId: %s", accountId))
        );
    }

    protected Currency getCurrencyIfAvailable(String currencyCode) {
        return currencyRepository.findCurrencyByCurrencyCode(currencyCode).orElseThrow(
                () -> new CurrencyNotSupportedException(String.format("Provide currency is not supported: %s", currencyCode))
        );
    }

}
