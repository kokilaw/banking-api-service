package io.kokilaw.banking.controller;

import io.kokilaw.banking.dto.TransactionDTO;
import io.kokilaw.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by kokilaw on 2022-08-10
 */
@RestController
@RequestMapping("/accounts/{accountId}/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionDTO> getTransactions(@PathVariable long accountId) {
        return transactionService.getTransactions(accountId);
    }

    @PostMapping
    public TransactionDTO createTransaction(@PathVariable long accountId,
                                            @Valid @RequestBody TransactionDTO transactionDTO) {
        return transactionService.createTransaction(accountId, transactionDTO);
    }

    @GetMapping("/{transactionId}")
    public TransactionDTO getTransaction(@PathVariable long accountId,
                                         @PathVariable long transactionId) {
        return transactionService.getTransaction(accountId, transactionId);
    }

}
