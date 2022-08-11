package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.dto.TransactionDTO;
import io.kokilaw.banking.error.exception.InsufficientAccountBalanceException;
import io.kokilaw.banking.error.exception.TransactionNotFoundException;
import io.kokilaw.banking.repository.AccountRepository;
import io.kokilaw.banking.repository.CurrencyRepository;
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
public class DefaultTransactionService extends BaseService implements TransactionService {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    @Autowired
    public DefaultTransactionService(
            AccountService accountService,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            CurrencyRepository currencyRepository
    ) {
        super(accountRepository, currencyRepository);
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<TransactionDTO> getTransactions(long accountId) {
        Account account = getAccountIfAvailable(accountId);
        return transactionRepository.findAllByAccount(account)
                .stream()
                .map(TransactionMapper::mapToTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionDTO createTransaction(long accountId, TransactionDTO transactionDTO) {
        Account account = getAccountIfAvailable(accountId);

        if (isAccountBalanceNotSufficient(transactionDTO, account)) {
            throw new InsufficientAccountBalanceException(String.format("Account balance not sufficient to perform debit transaction of: %s", transactionDTO.getAmount().toString()));
        }

        Transaction transaction = TransactionMapper.mapToTransaction(transactionDTO, account);
        if (transactionDTO.getTransactionType() == TransactionType.CREDIT) {
            accountService.updateAccountBalance(accountId, account.getBalance().add(transactionDTO.getAmount()));
        } else {
            accountService.updateAccountBalance(accountId, account.getBalance().subtract(transactionDTO.getAmount()));
        }

        return TransactionMapper.mapToTransactionDTO(transactionRepository.save(transaction));
    }

    private boolean isAccountBalanceNotSufficient(TransactionDTO transactionDTO, Account account) {
        return transactionDTO.getTransactionType() == TransactionType.DEBIT && account.getBalance().compareTo(transactionDTO.getAmount()) < 0;
    }

    @Override
    public TransactionDTO getTransaction(long accountId, long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new TransactionNotFoundException(
                String.format("Transaction not found for id: %s", transactionId)
        ));
        return TransactionMapper.mapToTransactionDTO(transaction);
    }

}
