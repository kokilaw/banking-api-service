package io.kokilaw.banking.service;

import io.kokilaw.banking.dto.AccountDTO;

import java.math.BigDecimal;

/**
 * Created by kokilaw on 2022-08-09
 */
public interface AccountService {

    /**
     * Creates an account and returns the created account object
     *
     * @param account account object
     * @return the created account
     * @throws io.kokilaw.banking.error.exception.CurrencyNotSupportedException If {@link AccountDTO#getCurrencyCode()}
     *                                                                    is not supported
     */
    AccountDTO createAccount(AccountDTO account);

    /**
     * Returns the account if available
     *
     * @param accountId id of the account
     * @return the account object
     * @throws io.kokilaw.banking.error.exception.AccountNotFoundException If account is not found for
     *                                                               passed accountId
     */
    AccountDTO getAccount(Long accountId);

    /**
     * Updates the existing account and return updated account
     *
     * @param accountId id of the account
     * @param account   account object
     * @return the updated account object
     * @throws io.kokilaw.banking.error.exception.AccountNotFoundException      If account is not found for
     *                                                                    passed accountId
     * @throws io.kokilaw.banking.error.exception.CurrencyNotSupportedException If {@link AccountDTO#getCurrencyCode()}
     *                                                                    is not supported
     */
    AccountDTO updateAccount(Long accountId, AccountDTO account);

    /**
     * Updates the balance of the account
     *
     * @param accountId id of the account
     * @param balance   balance value needs to be updated with
     * @return the updated account object
     * @throws io.kokilaw.banking.error.exception.AccountNotFoundException If account is not found for
     *                                                               passed accountId
     */
    AccountDTO updateAccountBalance(Long accountId, BigDecimal balance);

    /**
     * Deletes the account if available
     *
     * @param accountId id of the account needs to be deleted
     * @throws io.kokilaw.banking.error.exception.AccountNotFoundException If account is not found for
     *                                                               passed accountId
     */
    void deleteAccount(Long accountId);

}
