package io.kokilaw.banking.repository;

import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kokilaw on 2022-08-09
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAllByAccountOrderByCreatedAtDesc(Account account);
}
