package io.kokilaw.banking.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by kokilaw on 2022-08-09
 */
@Data
public class AccountDTO {

    private Long id;
    private String givenName;
    private String familyName;
    private String dateOfBirth;
    private String nic;
    private String currencyCode;
    private BigDecimal balance = BigDecimal.ZERO;

}
