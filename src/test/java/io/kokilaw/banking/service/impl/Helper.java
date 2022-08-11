package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.dto.AccountDTO;
import io.kokilaw.banking.dto.TransactionDTO;
import io.kokilaw.banking.dto.UserDTO;
import io.kokilaw.banking.repository.model.TransactionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by kokilaw on 2022-08-12
 */
public class Helper {

    private Helper() {

    }

    public static AccountDTO getAccountDTO() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setUserId(1L);
        accountDTO.setCurrencyCode("USD");
        accountDTO.setBalanceInCents(100L);
        return accountDTO;
    }

    public static UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("johndoe@gmail.com");
        userDTO.setDateOfBirth("1996-02-28");
        userDTO.setGivenName("John");
        userDTO.setFamilyName("Doe");
        userDTO.setNic("960345240v");
        return userDTO;
    }

    public static TransactionDTO getTransactionDTO() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(1L);
        transactionDTO.setTransactionType(TransactionType.DEBIT);
        transactionDTO.setAmountInCents(200L);
        transactionDTO.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return transactionDTO;
    }
}
