package io.kokilaw.banking.repository;

import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.User;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by kokilaw on 2022-08-12
 */
@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("When an account is saved, saved account is returned")
    public void whenAccountIsSaved_ReturnSavedAccount() {

        User user = Helper.getUser();
        user.setNic("633295239v");
        user.setEmail("test3@gmail.com");
        User savedUser = userRepository.save(user);
        Account account = Helper.getAccount();
        account.setUser(savedUser);
        Account savedAccount = accountRepository.save(account);

        Optional<Account> savedAccountOptional = accountRepository.findById(savedAccount.getId());
        Assertions.assertTrue(savedAccountOptional.isPresent());

        savedAccountOptional.ifPresent(savedAccountData -> {
            Assertions.assertEquals(account.getCurrencyCode(), savedAccountData.getCurrencyCode());
            Assertions.assertEquals(account.getBalance(), savedAccountData.getBalance());
        });

    }

    @Test
    @Transactional
    @DisplayName("When an account positive amount is added to account balance")
    public void whenPositiveAmountIsAddedToAccountBalance() {

        User user = Helper.getUser();
        user.setNic("683295239v");
        user.setEmail("test@gmail.com");
        User savedUser = userRepository.save(user);
        Account account = Helper.getAccount();
        account.setUser(savedUser);
        Account savedAccount = accountRepository.save(account);

        long amountAdding = 5L;
        int rowsAffected = accountRepository.addToAccountBalance(savedAccount.getId(), 5L);
        Assertions.assertEquals(1, rowsAffected);

        accountRepository.findById(savedAccount.getId())
                .ifPresent(updatedAccount -> {
                    Assertions.assertEquals(account.getBalance() + amountAdding, updatedAccount.getBalance());
                });


    }

    @Test
    @Transactional
    @DisplayName("When an account negative amount is added to account balance")
    public void whenNegativeAmountIsAddedToAccountBalance() {

        User user = Helper.getUser();
        user.setNic("683275239v");
        user.setEmail("tes7t@gmail.com");
        User savedUser = userRepository.save(user);
        Account account = Helper.getAccount();
        account.setUser(savedUser);
        Account savedAccount = accountRepository.save(account);

        long amountAdding = -5L;
        int rowsAffected = accountRepository.addToAccountBalance(savedAccount.getId(), -5L);
        Assertions.assertEquals(1, rowsAffected);

        accountRepository.findById(savedAccount.getId())
                .ifPresent(updatedAccount -> {
                    Assertions.assertEquals(account.getBalance() + amountAdding, updatedAccount.getBalance());
                });


    }

}
