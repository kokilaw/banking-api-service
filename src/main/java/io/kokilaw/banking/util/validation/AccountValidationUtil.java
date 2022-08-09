package io.kokilaw.banking.util.validation;

import io.kokilaw.banking.dto.AccountDTO;
import io.kokilaw.banking.exception.AccountValidationException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by kokilaw on 2022-08-09
 */
public class AccountValidationUtil {

    private AccountValidationUtil() {
    }

    private static void validateBlankString(String value, String message) {
        if (StringUtils.isEmpty(value)) {
            throw new AccountValidationException(message);
        }
    }

    public static void validateAccount(AccountDTO accountDTO) {

        validateBlankString(accountDTO.getGivenName(), "givenName cannot be null or empty");
        validateBlankString(accountDTO.getFamilyName(), "familyName cannot be null or empty");
        validateBlankString(accountDTO.getDateOfBirth(), "dateOfBirth cannot be null or empty");
        validateBlankString(accountDTO.getNic(), "nic cannot be null or empty");
        validateBlankString(accountDTO.getCurrencyCode(), "currencyCode cannot be null or empty");
        validateBlankString(accountDTO.getBalance(), "balance cannot be null or empty");

        BigDecimal bigDecimal = new BigDecimal(accountDTO.getBalance());
        if (bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountValidationException("Invalid amount passed for balance: " + accountDTO.getBalance());
        }

        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse(accountDTO.getDateOfBirth());
        } catch (DateTimeParseException e) {
            throw new AccountValidationException("Date of birth is not in valid format yyyy-mm-dd: " + accountDTO.getDateOfBirth());
        }

    }

}
