package io.kokilaw.banking.repository;

import io.kokilaw.banking.dto.TransactionDTO;
import io.kokilaw.banking.repository.model.Account;
import io.kokilaw.banking.repository.model.Transaction;
import io.kokilaw.banking.repository.model.TransactionType;
import io.kokilaw.banking.repository.model.User;
import io.kokilaw.banking.util.mapper.TransactionMapper;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

/**
 * Created by kokilaw on 2022-08-12
 */
@SpringBootTest
public class TransactionRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private User commonUser;
    private Account commonAccount;

    @BeforeEach
    public void doAfterEachTest() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("When an transaction is saved, saved transaction is returned")
    public void whenTransactionIsSaved_ReturnSavedTransaction() {

        User user = getCommonUser();
        Account account = getAccount(user);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmountInCents(100L);
        transactionDTO.setTransactionType(TransactionType.CREDIT);
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDTO, account);
        Transaction savedTransaction = transactionRepository.save(transaction);

        Optional<Transaction> savedEntityOptional = transactionRepository.findById(savedTransaction.getId());
        Assertions.assertTrue(savedEntityOptional.isPresent());

        savedEntityOptional.ifPresent(savedEntity -> {
            Assertions.assertEquals(transaction.getTransactionType(), savedEntity.getTransactionType());
            Assertions.assertEquals(transaction.getAmount(), savedEntity.getAmount());
        });

    }

    @Test
    @DisplayName("When an multiple transactions are saved, saved transactions are returned")
    public void whenMultipleTransactionAreSaved_ReturnSavedTransactions() {

        User user = getCommonUser();
        Account account = getAccount(user);

        TransactionDTO transactionDTOOne = new TransactionDTO();
        transactionDTOOne.setAmountInCents(100L);
        transactionDTOOne.setTransactionType(TransactionType.CREDIT);
        Transaction transactionOne = TransactionMapper.mapToTransaction(transactionDTOOne, account);

        TransactionDTO transactionDTOTwo = new TransactionDTO();
        transactionDTOTwo.setAmountInCents(150L);
        transactionDTOTwo.setTransactionType(TransactionType.DEBIT);
        Transaction transactionTwo = TransactionMapper.mapToTransaction(transactionDTOTwo, account);

        transactionRepository.save(transactionOne);
        transactionRepository.save(transactionTwo);

        List<Transaction> allTransactionsForAccount = transactionRepository.findAllByAccountOrderByCreatedAtDesc(account);
        Assertions.assertEquals(2, allTransactionsForAccount.size());

        Assertions.assertEquals(150L, allTransactionsForAccount.get(0).getAmount());
        Assertions.assertEquals(TransactionType.DEBIT, allTransactionsForAccount.get(0).getTransactionType());

        Assertions.assertEquals(100L, allTransactionsForAccount.get(1).getAmount());
        Assertions.assertEquals(TransactionType.CREDIT, allTransactionsForAccount.get(1).getTransactionType());



    }

    private User getCommonUser() {
      if (this.commonUser == null) {
          this.commonUser = userRepository.save(Helper.getUser());
      }
      return this.commonUser;
    }

    private Account getAccount(User user) {
        if (commonAccount == null) {
            Account accountToSave = Helper.getAccount();
            accountToSave.setUser(user);
            this.commonAccount = accountRepository.save(accountToSave);
        }
        return this.commonAccount;
    }

}
