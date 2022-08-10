package io.kokilaw.banking.repository.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by kokilaw on 2022-08-11
 */
@Entity
@Table(name = "currency")
@Data
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_code", nullable = false, unique = true)
    private String currencyCode;

    private String description;

}
