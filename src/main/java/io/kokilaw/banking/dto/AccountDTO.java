package io.kokilaw.banking.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

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
    private String balance;

}
