package io.kokilaw.banking.service;

import io.kokilaw.banking.dto.TransactionDTO;
import io.kokilaw.banking.error.exception.NotFoundException;

import java.util.List;

/**
 * Created by kokilaw on 2022-08-10
 */
public interface TransactionService {

    List<TransactionDTO> getTransactions(long accountId);

    TransactionDTO createTransaction(long accountId, TransactionDTO transactionDTO);

    TransactionDTO getTransaction(long accountId, long transactionId);

}
