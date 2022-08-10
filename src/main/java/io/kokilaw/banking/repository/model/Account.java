package io.kokilaw.banking.repository.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by kokilaw on 2022-08-09
 */
@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "given_name", nullable = false)
    private String givenName;

    @Column(name = "family_name", nullable = false)
    private String familyName;

    @Column(nullable = false, unique = true)
    private String nic;

    @Column(name = "dob", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt = LocalDateTime.now();


}
