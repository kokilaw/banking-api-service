package io.kokilaw.banking.dto;

import io.kokilaw.banking.repository.model.TransactionType;
import lombok.Data;

/**
 * Created by kokilaw on 2022-08-10
 */
@Data
public class TransactionDTO {
    private TransactionType transactionType;
    private String amount;
    private String createdAt;
}
