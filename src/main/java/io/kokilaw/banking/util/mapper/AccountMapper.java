package io.kokilaw.banking.util.mapper;

import io.kokilaw.banking.dto.AccountDTO;
import io.kokilaw.banking.repository.model.Account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by kokilaw on 2022-08-10
 */
public class AccountMapper {

    private AccountMapper() {
    }

    public static Account mapToAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setGivenName(accountDTO.getGivenName());
        account.setFamilyName(accountDTO.getFamilyName());
        account.setNic(accountDTO.getNic());
        account.setBalance(accountDTO.getBalance());
        account.setDateOfBirth(LocalDate.parse(accountDTO.getDateOfBirth()));
        return account;
    }

    public static AccountDTO mapToAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setGivenName(account.getGivenName());
        accountDTO.setFamilyName(account.getFamilyName());
        accountDTO.setNic(account.getNic());
        accountDTO.setCurrencyCode(account.getCurrency().getCurrencyCode());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setDateOfBirth(account.getDateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE));
        return accountDTO;
    }

}
