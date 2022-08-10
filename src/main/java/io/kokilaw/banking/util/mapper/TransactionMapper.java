package io.kokilaw.banking.util.mapper;

import io.kokilaw.banking.dto.TransactionDTO;
import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.Transaction;

import java.time.format.DateTimeFormatter;

/**
 * Created by kokilaw on 2022-08-11
 */
public class TransactionMapper {

    private TransactionMapper() {

    }

    public static TransactionDTO mapToTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setCreatedAt(transaction.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return transactionDTO;
    }

    public static Transaction mapToTransaction(TransactionDTO transactionDTO, Account account) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setAccount(account);
        return transaction;
    }

}
