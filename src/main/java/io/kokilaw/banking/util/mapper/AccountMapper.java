package io.kokilaw.banking.util.mapper;

import io.kokilaw.banking.dto.AccountDTO;
import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by kokilaw on 2022-08-10
 */
public class AccountMapper {

    private AccountMapper() {
    }

    public static Account mapToAccount(AccountDTO accountDTO, User user, String currencyCode) {
        Account account = new Account();
        account.setBalance(accountDTO.getBalanceInCents());
        account.setCurrencyCode(currencyCode);
        account.setUser(user);
        return account;
    }

    public static AccountDTO mapToAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setUserId(account.getUser().getId());
        accountDTO.setCurrencyCode(account.getCurrencyCode());
        accountDTO.setBalanceInCents(account.getBalance());
        return accountDTO;
    }

}
