package io.kokilaw.banking.service;

import io.kokilaw.banking.dto.TransactionDTO;

import java.util.List;

/**
 * Created by kokilaw on 2022-08-10
 */
public interface TransactionService {

    /**
     * Returns all the transactions for an account
     *
     * @param accountId id of the account
     * @return list of available transactions for account
     * @throws io.kokilaw.banking.exception.AccountNotFoundException If account is not found for
     *                                                               passed accountId
     */
    List<TransactionDTO> getTransactions(Long accountId);

    /**
     * Creates transaction for an account
     *
     * @param accountId      id of the account
     * @param transactionDTO transaction object
     * @return the created transaction
     * @throws io.kokilaw.banking.exception.AccountNotFoundException            If account is not found for
     *                                                                          passed accountId
     * @throws io.kokilaw.banking.exception.InsufficientAccountBalanceException If account does contain sufficient funds
     *                                                                          to proceed with transaction
     */
    TransactionDTO createTransaction(Long accountId, TransactionDTO transactionDTO);

    /**
     * Returns a specific transaction for an account
     *
     * @param accountId     id of the account
     * @param transactionId id of the transaction
     * @return the transaction
     * @throws io.kokilaw.banking.exception.AccountNotFoundException     If account is not found for
     *                                                                   passed accountId
     * @throws io.kokilaw.banking.exception.TransactionNotFoundException If transaction is not found for
     *                                                                   passed transactionId
     */
    TransactionDTO getTransaction(Long accountId, Long transactionId);

}
