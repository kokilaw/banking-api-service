package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.dto.AccountDTO;
import io.kokilaw.banking.dto.UserDTO;
import io.kokilaw.banking.repository.AccountRepository;
import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.User;
import io.kokilaw.banking.service.AccountService;
import io.kokilaw.banking.util.mapper.AccountMapper;
import io.kokilaw.banking.util.mapper.UserMapper;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * Created by kokilaw on 2022-08-11
 */
@SpringBootTest
public class AccountServiceTest {

    @MockBean
    private CommonService commonService;

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName("When new account is getting created")
    public void whenNewAccountIsGettingCreated() {

        UserDTO userDTO = getUserDTO();
        User user = UserMapper.mapToUser(userDTO);
        AccountDTO accountDTO = getAccountDTO();
        Account account = AccountMapper.mapToAccount(accountDTO, user, "USD");

        Mockito.when(commonService.getCurrencyIfAvailable("USD")).thenReturn("USD");
        Mockito.when(commonService.getUserIfAvailable(1L)).thenReturn(user);
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        AccountDTO createdAccount = accountService.createAccount(accountDTO);
        Mockito.verify(commonService, Mockito.times(1)).getUserIfAvailable(accountDTO.getUserId());
        Mockito.verify(commonService, Mockito.times(1)).getCurrencyIfAvailable(accountDTO.getCurrencyCode());
        Mockito.verify(accountRepository, Mockito.times(1)).save(account);

        Assert.assertEquals(accountDTO.getBalanceInCents(), createdAccount.getBalanceInCents());
        Assert.assertEquals(accountDTO.getCurrencyCode(), createdAccount.getCurrencyCode());
        Assert.assertEquals(accountDTO.getUserId(), createdAccount.getUserId());
        Assert.assertEquals(accountDTO.getId(), createdAccount.getId());

    }

    @Test
    @DisplayName("When existing account is requested")
    public void whenExistingAccountIsRequested() {

        UserDTO userDTO = getUserDTO();
        User user = UserMapper.mapToUser(userDTO);
        AccountDTO accountDTO = getAccountDTO();
        Account account = AccountMapper.mapToAccount(accountDTO, user, "USD");

        Mockito.when(commonService.getAccountIfAvailable(1L)).thenReturn(account);

        AccountDTO fetchedAccount = accountService.getAccount(1L);
        Mockito.verify(commonService, Mockito.times(1)).getAccountIfAvailable(1L);

        Assert.assertEquals(accountDTO.getBalanceInCents(), fetchedAccount.getBalanceInCents());
        Assert.assertEquals(accountDTO.getCurrencyCode(), fetchedAccount.getCurrencyCode());
        Assert.assertEquals(accountDTO.getUserId(), fetchedAccount.getUserId());
        Assert.assertEquals(accountDTO.getId(), fetchedAccount.getId());

    }

    @Test
    @DisplayName("When existing account is updated")
    public void whenExistingAccountIsUpdated() {

        UserDTO userDTO = getUserDTO();
        User user = UserMapper.mapToUser(userDTO);
        AccountDTO accountDTO = getAccountDTO();
        Account account = AccountMapper.mapToAccount(accountDTO, user, "USD");

        AccountDTO updateAccountDTO = getAccountDTO();
        updateAccountDTO.setBalanceInCents(500L);
        Account updateAccount = AccountMapper.mapToAccount(updateAccountDTO, user, "USD");

        Mockito.when(commonService.getAccountIfAvailable(1L)).thenReturn(account);
        Mockito.when(commonService.getCurrencyIfAvailable("USD")).thenReturn("USD");
        Mockito.when(accountRepository.save(updateAccount)).thenReturn(updateAccount);

        AccountDTO updatedAccount = accountService.updateAccount(1L, updateAccountDTO);
        Mockito.verify(commonService, Mockito.times(1)).getAccountIfAvailable(1L);
        Mockito.verify(commonService, Mockito.times(1)).getCurrencyIfAvailable("USD");
        Mockito.verify(accountRepository, Mockito.times(1)).save(updateAccount);

        Assert.assertEquals(updateAccountDTO.getBalanceInCents(), updatedAccount.getBalanceInCents());
        Assert.assertEquals(updateAccountDTO.getCurrencyCode(), updatedAccount.getCurrencyCode());
        Assert.assertEquals(updateAccountDTO.getUserId(), updatedAccount.getUserId());
        Assert.assertEquals(updateAccountDTO.getId(), updatedAccount.getId());

    }

    @Test
    @DisplayName("When amount is added to account balance and update is successful")
    public void whenAmountIsAddedToAccountBalance_AndUpdateIsSuccess() {
        Mockito.when(accountRepository.addToAccountBalance(1L, 100L)).thenReturn(1);
        boolean result = accountService.addToAccountBalance(1L, 100L);
        Assert.assertEquals(true, result);
    }

    @Test
    @DisplayName("When amount is added to account balance and update is  not successful")
    public void whenAmountIsAddedToAccountBalance_AndUpdateIsNotSuccess() {
        Mockito.when(accountRepository.addToAccountBalance(2L, 110L)).thenReturn(0);
        boolean result = accountService.addToAccountBalance(2L, 110L);
        Assert.assertEquals(false, result);
    }

    @Test
    @DisplayName("When deletion of existing account is requested")
    public void whenDeletionOfExistingAccountIsRequested() {

        UserDTO userDTO = getUserDTO();
        User user = UserMapper.mapToUser(userDTO);
        AccountDTO accountDTO = getAccountDTO();
        Account account = AccountMapper.mapToAccount(accountDTO, user, "USD");

        Mockito.when(commonService.getAccountIfAvailable(1L)).thenReturn(account);

        accountService.deleteAccount(1L);
        Mockito.verify(commonService, Mockito.times(1)).getAccountIfAvailable(1L);
        Mockito.verify(accountRepository, Mockito.times(1)).deleteById(1L);

    }

    private AccountDTO getAccountDTO() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setUserId(1L);
        accountDTO.setCurrencyCode("USD");
        accountDTO.setBalanceInCents(100L);
        return accountDTO;
    }

    private UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("johndoe@gmail.com");
        userDTO.setDateOfBirth("1996-02-28");
        userDTO.setGivenName("John");
        userDTO.setFamilyName("Doe");
        userDTO.setNic("960345240v");
        return userDTO;
    }

}
