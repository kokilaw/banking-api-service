package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.dto.AccountDTO;
import io.kokilaw.banking.exception.AccountNotFoundException;
import io.kokilaw.banking.exception.CurrencyNotSupportedException;
import io.kokilaw.banking.repository.AccountRepository;
import io.kokilaw.banking.repository.CurrencyRepository;
import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.Currency;
import io.kokilaw.banking.service.AccountService;
import io.kokilaw.banking.util.validation.AccountValidationUtil;
import io.kokilaw.banking.util.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by kokilaw on 2022-08-09
 */

@Service
public class DefaultAccountService implements AccountService {

    private final AccountRepository accountRepository;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public DefaultAccountService(AccountRepository accountRepository, CurrencyRepository currencyRepository) {
        this.accountRepository = accountRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        AccountValidationUtil.validateAccount(accountDTO);
        Account account = AccountMapper.mapToAccount(accountDTO);
        Currency currency = getCurrencyIfAvailable(accountDTO.getCurrencyCode());
        account.setCurrency(currency);
        return AccountMapper.mapToAccountDTO(accountRepository.save(account));
    }

    @Override
    public AccountDTO getAccount(Long accountId) {
        Account account = getAccountIfAvailable(accountId);
        return AccountMapper.mapToAccountDTO(account);
    }

    @Override
    public AccountDTO updateAccount(Long accountId, AccountDTO accountDTO) {
        Account currentAccount = getAccountIfAvailable(accountId);
        Currency currency = getCurrencyIfAvailable(accountDTO.getCurrencyCode());
        AccountValidationUtil.validateAccount(accountDTO);
        Account accountToUpdate = AccountMapper.mapToAccount(accountDTO);
        currentAccount.setBalance(accountToUpdate.getBalance());
        currentAccount.setCurrency(currency);
        currentAccount.setGivenName(accountToUpdate.getGivenName());
        currentAccount.setFamilyName(accountToUpdate.getFamilyName());
        currentAccount.setNic(accountToUpdate.getNic());
        currentAccount.setUpdatedAt(LocalDateTime.now());
        return AccountMapper.mapToAccountDTO(accountRepository.save(currentAccount));
    }

    @Override
    public AccountDTO updateAccountBalance(Long accountId, BigDecimal balance) {
        Account currentAccount = getAccountIfAvailable(accountId);
        currentAccount.setBalance(balance);
        return AccountMapper.mapToAccountDTO(accountRepository.save(currentAccount));
    }

    @Override
    public void deleteAccount(Long accountId) {
        getAccountIfAvailable(accountId);
        accountRepository.deleteById(accountId);
    }

    private Account getAccountIfAvailable(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException("Account not found with accountId: " + accountId)
        );
    }

    private Currency getCurrencyIfAvailable(String currencyCode) {
        return currencyRepository.findCurrencyByCurrencyCode(currencyCode).orElseThrow(
                () -> new CurrencyNotSupportedException("Provide currency is not supported: " + currencyCode)
        );
    }


}
