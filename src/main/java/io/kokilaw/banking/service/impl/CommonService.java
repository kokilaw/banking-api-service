package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.error.exception.BankingApiExceptions;
import io.kokilaw.banking.repository.AccountRepository;
import io.kokilaw.banking.repository.UserRepository;
import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by kokilaw on 2022-08-11
 */
@Service
public class CommonService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Value("${application.properties.supported-currencies}")
    private String[] supportedCurrencies;

    @Autowired
    public CommonService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    protected Account getAccountIfAvailable(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> BankingApiExceptions.generateEntityNotFoundException(Account.class.getSimpleName(), accountId)
        );
    }

    protected User getUserIfAvailable(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> BankingApiExceptions.generateEntityNotFoundException(User.class.getSimpleName(), userId)
        );
    }

    protected String getCurrencyIfAvailable(String currencyCode) {
        return Arrays.stream(supportedCurrencies)
                .filter(configCurrencyCode -> configCurrencyCode.equals(currencyCode))
                .findAny()
                .orElseThrow(() -> BankingApiExceptions.generateCurrencyNotSupportedException(currencyCode));
    }

}
