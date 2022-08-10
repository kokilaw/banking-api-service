package io.kokilaw.banking.dto;

import io.kokilaw.banking.repository.model.TransactionType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Created by kokilaw on 2022-08-10
 */
@Data
public class TransactionDTO {

    private Long id;

    @NotNull(message = "Transaction type should be CREDIT or DEBIT")
    private TransactionType transactionType;

    @Positive(message = "Transaction amount cannot be a negative value")
    private BigDecimal amount = BigDecimal.ZERO;

    private String createdAt;

}
