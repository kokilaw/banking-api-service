package io.kokilaw.banking.repository;

import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.User;

import java.time.LocalDate;

/**
 * Created by kokilaw on 2022-08-12
 */
public class Helper {

    private Helper() {

    }

    public static User getUser() {
        User user = new User();
        user.setId(100L);
        user.setGivenName("John");
        user.setFamilyName("Doe");
        user.setNic("943053245v");
        user.setDateOfBirth(LocalDate.of(1999, 2, 19));
        user.setEmail("johndoe@gmail.com");
        return user;
    }

    public static Account getAccount() {
        Account account = new Account();
        account.setBalance(50L);
        account.setCurrencyCode("USD");
        return account;
    }

}
