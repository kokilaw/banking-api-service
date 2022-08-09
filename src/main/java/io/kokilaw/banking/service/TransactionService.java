package io.kokilaw.banking.service;

import io.kokilaw.banking.dto.TransactionDTO;

import java.util.List;

/**
 * Created by kokilaw on 2022-08-10
 */
public interface TransactionService {

    List<TransactionDTO> getTransactions(Long accountId);

    TransactionDTO createTransaction(Long accountId, TransactionDTO transactionDTO);

    TransactionDTO getTransaction(Long accountId, Long transactionId);

}
