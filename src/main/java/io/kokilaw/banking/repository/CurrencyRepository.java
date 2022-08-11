package io.kokilaw.banking.repository;

import io.kokilaw.banking.repository.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by kokilaw on 2022-08-11
 */
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<Currency> findCurrencyByCurrencyCode(String currencyCode);
}
