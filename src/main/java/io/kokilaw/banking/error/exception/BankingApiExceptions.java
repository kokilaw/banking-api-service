package io.kokilaw.banking.error.exception;

import io.kokilaw.banking.error.ApiError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

/**
 * Created by kokilaw on 2022-08-11
 */
public class BankingApiExceptions {

    public static BankingApiException generateCurrencyNotSupportedException(String currencyCode) {
        return new BankingApiException(
                ApiError.CURRENCY_NOT_SUPPORTED_ERROR,
                String.format("Provide currency is not supported: %s", currencyCode)
        );
    }

    public static BankingApiException generateInSufficientAccountBalanceException(Long transactionAmountInCents) {
        return new BankingApiException(
                ApiError.INSUFFICIENT_ACCOUNT_BALANCE_ERROR,
                String.format("Account balance not sufficient to perform debit transaction of: %s", transactionAmountInCents)
        );
    }

    public static BankingApiException generateEntityNotFoundException(String entityName, Long entityId) {
        return new BankingApiException(
                ApiError.ENTITY_NOT_FOUND_ERROR,
                String.format("%s not found with id: %s", entityName, entityId)
        );
    }

    public static BankingApiException generateEntityValidationFailedException(String entityName, List<String> errorMessages) {
        return new BankingApiException(
                ApiError.ENTITY_VALIDATION_FAILED_ERROR,
                String.format("%s validation failed", entityName),
                errorMessages
        );
    }

    public static BankingApiException generateTransactionFailedException(Long accountId) {
        return new BankingApiException(
                ApiError.TRANSACTION_FAILED_ERROR,
                String.format("Transaction failed to execute on account: %s", accountId)
        );
    }

    public static BankingApiException generateInvalidRequestException(String additionalInfo) {
        return new BankingApiException(
                ApiError.INVALID_REQUEST,
                additionalInfo
        );
    }

    public static BankingApiException generateConstraintViolationException(
            ConstraintViolationException constraintViolationException,
            WebRequest webRequest
    ) {
        switch (constraintViolationException.getConstraintName()) {
            case "positive_account_balance":
                return new BankingApiException(
                        ApiError.INSUFFICIENT_ACCOUNT_BALANCE_ERROR,
                        null
                );
            case "bank_user_nic_key":
                return new BankingApiException(
                        ApiError.USER_EMAIL_AlREADY_IN_USE_ERROR,
                        null
                );
            case "bank_user_email_key":
                return new BankingApiException(
                        ApiError.USER_NIC_AlREADY_IN_USE_ERROR,
                        null
                );
            default:
                return new BankingApiException(
                        ApiError.CONSTRAINT_VIOLATION,
                        null
                );
        }
    }


}
