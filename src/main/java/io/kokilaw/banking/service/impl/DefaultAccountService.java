package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.dto.AccountDTO;
import io.kokilaw.banking.repository.AccountRepository;
import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.User;
import io.kokilaw.banking.service.AccountService;
import io.kokilaw.banking.util.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kokilaw on 2022-08-09
 */

@Service
public class DefaultAccountService implements AccountService {

    private final CommonService commonService;
    private final AccountRepository accountRepository;

    @Autowired
    public DefaultAccountService(CommonService commonService, AccountRepository accountRepository) {
        this.commonService = commonService;
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        String currencyCode = commonService.getCurrencyIfAvailable(accountDTO.getCurrencyCode());
        User user = commonService.getUserIfAvailable(accountDTO.getUserId());
        Account account = AccountMapper.mapToAccount(accountDTO, user, currencyCode);
        return AccountMapper.mapToAccountDTO(accountRepository.save(account));
    }

    @Override
    public AccountDTO getAccount(long accountId) {
        Account account = commonService.getAccountIfAvailable(accountId);
        return AccountMapper.mapToAccountDTO(account);
    }

    @Override
    public AccountDTO updateAccount(long accountId, AccountDTO accountDTO) {
        Account currentAccount = commonService.getAccountIfAvailable(accountId);
        String currency = commonService.getCurrencyIfAvailable(accountDTO.getCurrencyCode());
        Account accountToUpdate = AccountMapper.mapToAccount(accountDTO, currentAccount.getUser(), currency);
        currentAccount.setBalance(accountToUpdate.getBalance());
        currentAccount.setCurrencyCode(accountToUpdate.getCurrencyCode());
        return AccountMapper.mapToAccountDTO(accountRepository.save(currentAccount));
    }

    @Override
    public AccountDTO updateAccountBalance(long accountId, long balance) {
        Account currentAccount = commonService.getAccountIfAvailable(accountId);
        currentAccount.setBalance(balance);
        return AccountMapper.mapToAccountDTO(accountRepository.save(currentAccount));
    }

    @Override
    public void deleteAccount(long accountId) {
        commonService.getAccountIfAvailable(accountId);
        accountRepository.deleteById(accountId);
    }


}
