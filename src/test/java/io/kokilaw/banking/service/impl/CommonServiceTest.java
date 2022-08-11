package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.error.ApiError;
import io.kokilaw.banking.error.exception.BankingApiException;
import io.kokilaw.banking.repository.AccountRepository;
import io.kokilaw.banking.repository.UserRepository;
import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by kokilaw on 2022-08-11
 */
@SpringBootTest
public class CommonServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private CommonService commonService;

    private User user;
    private Account account;

    @BeforeEach
    public void setup() {

        user = new User();
        user.setGivenName("John");
        user.setFamilyName("Doe");

        account = new Account();
        account.setUser(user);
        account.setCurrencyCode("USD");
        account.setBalance(100L);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.of(account));

    }

    @Test
    @DisplayName("When user is not found for given user id")
    public void whenUserIsNotFound() {
        BankingApiException bankingApiException = assertThrows(BankingApiException.class, () -> {
            commonService.getUserIfAvailable(1L);
        });
        Assert.assertEquals(bankingApiException.getHttpStatus(), ApiError.ENTITY_NOT_FOUND_ERROR.getHttpStatus());
    }

    @Test
    @DisplayName("When user is found for given user id")
    public void whenUserIsFound() {
        User returnedUser = commonService.getUserIfAvailable(2L);
        Assert.assertEquals(user.getFamilyName(), returnedUser.getFamilyName());
        Assert.assertEquals(user.getGivenName(), returnedUser.getGivenName());
    }

    @Test
    @DisplayName("When account is not found for given account id")
    public void whenAccountIsNotFound() {
        BankingApiException bankingApiException = assertThrows(BankingApiException.class, () -> {
            commonService.getAccountIfAvailable(1L);
        });
        Assert.assertEquals(bankingApiException.getHttpStatus(), ApiError.ENTITY_NOT_FOUND_ERROR.getHttpStatus());
    }

    @Test
    @DisplayName("When account is found for given account id")
    public void whenAccountIsFound() {
        Account returnedAccount = commonService.getAccountIfAvailable(2L);
        Assert.assertEquals(account.getCurrencyCode(), returnedAccount.getCurrencyCode());
        Assert.assertEquals(account.getBalance(), returnedAccount.getBalance());
    }

    @Test
    @DisplayName("When passed currency code is not supported")
    public void whenCurrencyCodeIsNotSupported() {
        BankingApiException bankingApiException = assertThrows(BankingApiException.class, () -> {
            commonService.getCurrencyIfAvailable("SGD");
        });
        Assert.assertEquals(bankingApiException.getHttpStatus(), ApiError.CURRENCY_NOT_SUPPORTED_ERROR.getHttpStatus());
    }

    @Test
    @DisplayName("When passed currency code is supported")
    public void whenCurrencyCodeIsSupported() {
        String currencyCode = commonService.getCurrencyIfAvailable("USD");
        Assert.assertEquals("USD", currencyCode);
    }

}