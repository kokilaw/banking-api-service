package io.kokilaw.banking.repository;

import io.kokilaw.banking.repository.model.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by kokilaw on 2022-08-09
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    @Modifying
    @Query("UPDATE Account account SET account.balance = account.balance + :amount WHERE account.id = :accountId")
    int addToAccountBalance(@Param("accountId") long accountId, @Param("amount") long amount);

}
