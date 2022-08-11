CREATE TABLE currency
(
    id            SERIAL,
    currency_code VARCHAR(3) UNIQUE NOT NULL,
    description   text,
    PRIMARY KEY (id)
);
ALTER TABLE currency
    OWNER TO postgres;

CREATE TABLE account
(
    id          BIGSERIAL,
    given_name  VARCHAR(100)                NOT NULL,
    family_name VARCHAR(100)                NOT NULL,
    nic         VARCHAR(10)                 NOT NULL,
    dob         DATE                        NOT NULL,
    balance     NUMERIC(15, 6)              NOT NULL,
    currency_id SERIAL                      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (currency_id) REFERENCES currency (id)
);
ALTER TABLE account
    OWNER TO postgres;

CREATE TABLE transaction
(
    id               BIGSERIAL,
    account_id       BIGSERIAL                   NOT NULL,
    transaction_type VARCHAR(10)                 NOT NULL,
    amount           NUMERIC(15, 6)              NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account (id)
);
ALTER TABLE transaction
    OWNER TO postgres;