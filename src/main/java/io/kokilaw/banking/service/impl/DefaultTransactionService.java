package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.dto.AccountDTO;
import io.kokilaw.banking.dto.TransactionDTO;
import io.kokilaw.banking.service.AccountService;
import io.kokilaw.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kokilaw on 2022-08-10
 */
@Service
public class DefaultTransactionService implements TransactionService {

    private final AccountService accountService;

    @Autowired
    public DefaultTransactionService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public List<TransactionDTO> getTransactions(Long accountId) {
        AccountDTO account = accountService.getAccount(accountId);
        return null;
    }

    @Override
    public TransactionDTO createTransaction(Long accountId, TransactionDTO transactionDTO) {
        AccountDTO account = accountService.getAccount(accountId);
        return null;
    }

    @Override
    public TransactionDTO getTransaction(Long accountId, Long transactionId) {
        AccountDTO account = accountService.getAccount(accountId);
        return null;
    }

}
