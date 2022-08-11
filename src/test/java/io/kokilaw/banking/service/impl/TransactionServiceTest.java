package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.dto.AccountDTO;
import io.kokilaw.banking.dto.TransactionDTO;
import io.kokilaw.banking.dto.UserDTO;
import io.kokilaw.banking.error.ApiError;
import io.kokilaw.banking.error.exception.BankingApiException;
import io.kokilaw.banking.repository.TransactionRepository;
import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.Transaction;
import io.kokilaw.banking.repository.model.TransactionType;
import io.kokilaw.banking.repository.model.User;
import io.kokilaw.banking.service.AccountService;
import io.kokilaw.banking.service.TransactionService;
import io.kokilaw.banking.util.mapper.AccountMapper;
import io.kokilaw.banking.util.mapper.TransactionMapper;
import io.kokilaw.banking.util.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static io.kokilaw.banking.service.impl.Helper.getTransactionDTO;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by kokilaw on 2022-08-12
 */
@SpringBootTest
public class TransactionServiceTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private CommonService commonService;

    @Autowired
    private TransactionService transactionService;

    @Test
    @DisplayName("When the request transaction is not available")
    public void whenRequestedTransactionNotAvailable() {

        Mockito.when(transactionRepository.findById(2L)).thenReturn(Optional.empty());
        BankingApiException bankingApiException = assertThrows(BankingApiException.class, () -> {
            transactionService.getTransaction(1L, 2L);
        });

        Assertions.assertEquals(bankingApiException.getHttpStatus(), ApiError.ENTITY_NOT_FOUND_ERROR.getHttpStatus());
        Assertions.assertEquals(bankingApiException.getErrorResponse().getErrorCode(), ApiError.ENTITY_NOT_FOUND_ERROR.getErrorCode());

    }

    @Test
    @DisplayName("When the request transaction is available")
    public void whenRequestedTransactionAvailable() {

        UserDTO userDTO = Helper.getUserDTO();
        User user = UserMapper.mapToUser(userDTO);
        AccountDTO accountDTO = Helper.getAccountDTO();
        Account account = AccountMapper.mapToAccount(accountDTO, user, "USD");

        TransactionDTO transactionDTO = getTransactionDTO();
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDTO, account);
        transaction.setCreatedAt(LocalDateTime.now());


        Mockito.when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        TransactionDTO returnTransaction = transactionService.getTransaction(1L, 1L);

        Assertions.assertEquals(transactionDTO.getTransactionType(), returnTransaction.getTransactionType());
        Assertions.assertEquals(transactionDTO.getAmountInCents(), returnTransaction.getAmountInCents());
        Assertions.assertEquals(transactionDTO.getId(), returnTransaction.getId());

    }

    @Test
    @DisplayName("When the request transaction executes successfully")
    public void whenRequestedTransactionExecutesSuccessfully() {

        UserDTO userDTO = Helper.getUserDTO();
        User user = UserMapper.mapToUser(userDTO);
        AccountDTO accountDTO = Helper.getAccountDTO();
        accountDTO.setBalanceInCents(100L);
        Account account = AccountMapper.mapToAccount(accountDTO, user, "USD");

        TransactionDTO transactionDTO = getTransactionDTO();
        transactionDTO.setAmountInCents(50L);
        transactionDTO.setTransactionType(TransactionType.DEBIT);
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDTO, account);

        Mockito.when(commonService.getAccountIfAvailable(1L)).thenReturn(account);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Mockito.when(accountService.addToAccountBalance(1L, -50L)).thenReturn(true);

        TransactionDTO createdTransaction = transactionService.createTransaction(1L, transactionDTO);
        Mockito.verify(commonService, Mockito.times(1)).getAccountIfAvailable(1L);
        Mockito.verify(transactionRepository, Mockito.times(1)).save(transaction);
        Mockito.verify(accountService, Mockito.times(1)).addToAccountBalance(1L, -50L);

        Assertions.assertEquals(transactionDTO.getTransactionType(), createdTransaction.getTransactionType());
        Assertions.assertEquals(transactionDTO.getAmountInCents(), createdTransaction.getAmountInCents());
        Assertions.assertEquals(transactionDTO.getId(), createdTransaction.getId());

    }

    @Test
    @DisplayName("When the account balance is not sufficient to do requested transaction")
    public void whenAccountBalanceIsNotSufficient() {

        UserDTO userDTO = Helper.getUserDTO();
        User user = UserMapper.mapToUser(userDTO);
        AccountDTO accountDTO = Helper.getAccountDTO();
        accountDTO.setBalanceInCents(100L);
        Account account = AccountMapper.mapToAccount(accountDTO, user, "USD");

        TransactionDTO transactionDTO = getTransactionDTO();
        transactionDTO.setAmountInCents(150L);
        transactionDTO.setTransactionType(TransactionType.DEBIT);
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDTO, account);

        Mockito.when(commonService.getAccountIfAvailable(1L)).thenReturn(account);

        BankingApiException bankingApiException = assertThrows(BankingApiException.class, () -> {
            transactionService.createTransaction(1L, transactionDTO);
        });

        Assertions.assertEquals(bankingApiException.getHttpStatus(), ApiError.INSUFFICIENT_ACCOUNT_BALANCE_ERROR.getHttpStatus());
        Assertions.assertEquals(bankingApiException.getErrorResponse().getErrorCode(), ApiError.INSUFFICIENT_ACCOUNT_BALANCE_ERROR.getErrorCode());


    }

    @Test
    @DisplayName("When account balance update failed")
    public void whenAccountBalanceUpdateFailed() {

        UserDTO userDTO = Helper.getUserDTO();
        User user = UserMapper.mapToUser(userDTO);
        AccountDTO accountDTO = Helper.getAccountDTO();
        accountDTO.setBalanceInCents(100L);
        Account account = AccountMapper.mapToAccount(accountDTO, user, "USD");

        Mockito.when(commonService.getAccountIfAvailable(1L)).thenReturn(account);
        Mockito.when(accountService.addToAccountBalance(1L, 80L)).thenReturn(false);

        TransactionDTO transactionDTO = getTransactionDTO();
        transactionDTO.setAmountInCents(80L);
        transactionDTO.setTransactionType(TransactionType.CREDIT);
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDTO, account);

        BankingApiException bankingApiException = assertThrows(BankingApiException.class, () -> {
            transactionService.createTransaction(1L, transactionDTO);
            Mockito.verify(transactionRepository, Mockito.times(1)).save(transaction);
        });

        Assertions.assertEquals(bankingApiException.getHttpStatus(), ApiError.TRANSACTION_FAILED_ERROR.getHttpStatus());
        Assertions.assertEquals(bankingApiException.getErrorResponse().getErrorCode(), ApiError.TRANSACTION_FAILED_ERROR.getErrorCode());


    }

}
