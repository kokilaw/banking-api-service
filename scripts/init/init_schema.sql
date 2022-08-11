CREATE TABLE bank_user
(
    id          BIGSERIAL,
    given_name  VARCHAR(100)                NOT NULL,
    family_name VARCHAR(100)                NOT NULL,
    nic         VARCHAR(10)                 NOT NULL UNIQUE,
    email       VARCHAR(50)                 NOT NULL UNIQUE,
    dob         DATE                        NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);
ALTER TABLE bank_user
    OWNER TO postgres;

CREATE TABLE account
(
    id            BIGSERIAL,
    user_id       BIGINT                      NOT NULL,
    balance       BIGINT                      NOT NULL,
    currency_code VARCHAR(3)                  NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES bank_user (id),
    CONSTRAINT positive_account_balance CHECK (balance >= 0)
);
ALTER TABLE account
    OWNER TO postgres;

CREATE TABLE transaction
(
    id               BIGSERIAL,
    account_id       BIGINT                      NOT NULL,
    transaction_type VARCHAR(10)                 NOT NULL,
    amount           BIGINT                      NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account (id)
);
ALTER TABLE transaction
    OWNER TO postgres;