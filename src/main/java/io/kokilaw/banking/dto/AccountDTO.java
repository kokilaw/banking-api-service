package io.kokilaw.banking.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Created by kokilaw on 2022-08-09
 */
@Data
public class AccountDTO {

    private Long id;

    @NotNull(message = "User Id cannot be null")
    private Long userId;

    @NotEmpty(message = "Currency code cannot be empty")
    private String currencyCode;

    @Positive(message = "Account balance cannot be a negative value")
    private long balanceInCents = 0L;

}
