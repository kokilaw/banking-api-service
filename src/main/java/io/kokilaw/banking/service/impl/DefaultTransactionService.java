package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.dto.TransactionDTO;
import io.kokilaw.banking.error.exception.BankingApiExceptions;
import io.kokilaw.banking.repository.TransactionRepository;
import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.Transaction;
import io.kokilaw.banking.repository.model.TransactionType;
import io.kokilaw.banking.service.AccountService;
import io.kokilaw.banking.service.TransactionService;
import io.kokilaw.banking.util.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kokilaw on 2022-08-10
 */
@Service
public class DefaultTransactionService implements TransactionService {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final CommonService commonService;

    @Autowired
    public DefaultTransactionService(
            AccountService accountService,
            TransactionRepository transactionRepository,
            CommonService commonService) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.commonService = commonService;
    }

    @Override
    public List<TransactionDTO> getTransactions(long accountId) {
        Account account = commonService.getAccountIfAvailable(accountId);
        return transactionRepository.findAllByAccount(account)
                .stream()
                .map(TransactionMapper::mapToTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionDTO createTransaction(long accountId, TransactionDTO transactionDTO) {
        Account account = commonService.getAccountIfAvailable(accountId);

        long transactionAmountInCents = transactionDTO.getAmountInCents();
        if (isAccountBalanceNotSufficient(transactionDTO, account)) {
            throw BankingApiExceptions.generateInSufficientAccountBalanceException(transactionAmountInCents);
        }

        Transaction transaction = TransactionMapper.mapToTransaction(transactionDTO, account);
        Transaction savedTransaction = transactionRepository.save(transaction);
        boolean accountUpdated = accountService.addToAccountBalance(
                accountId,
                transactionDTO.getTransactionType() == TransactionType.CREDIT
                        ? transactionAmountInCents
                        : -transactionAmountInCents
        );

        if (!accountUpdated) {
            throw BankingApiExceptions.generateTransactionFailedException(accountId);
        }

        return TransactionMapper.mapToTransactionDTO(savedTransaction);
    }

    private boolean isAccountBalanceNotSufficient(TransactionDTO transactionDTO, Account account) {
        return transactionDTO.getTransactionType() == TransactionType.DEBIT && account.getBalance() < transactionDTO.getAmountInCents();
    }

    @Override
    public TransactionDTO getTransaction(long accountId, long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                BankingApiExceptions.generateEntityNotFoundException(Transaction.class.getSimpleName(), transactionId)
        );
        return TransactionMapper.mapToTransactionDTO(transaction);
    }

}
