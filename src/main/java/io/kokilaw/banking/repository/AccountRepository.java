package io.kokilaw.banking.repository;

import io.kokilaw.banking.repository.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kokilaw on 2022-08-09
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
}
