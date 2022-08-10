package io.kokilaw.banking.dto;

import io.kokilaw.banking.repository.model.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by kokilaw on 2022-08-10
 */
@Data
public class TransactionDTO {
    private TransactionType transactionType;
    private BigDecimal amount = BigDecimal.ZERO;
    private String createdAt;
}
