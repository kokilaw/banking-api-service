CREATE TABLE currency
(
    id            SERIAL,
    currency_code CHAR(3) UNIQUE NOT NULL,
    description   text,
    PRIMARY KEY (id)
);
ALTER TABLE currency
    OWNER TO postgres;

CREATE TABLE account
(
    id          BIGINT,
    given_name  VARCHAR(100)                NOT NULL,
    family_name VARCHAR(100)                NOT NULL,
    nic         CHAR(10)                    NOT NULL,
    dob         DATE                        NOT NULL,
    balance     NUMERIC                     NOT NULL,
    currency_id BIGINT                      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (currency_id) REFERENCES currency (id)
);
ALTER TABLE account
    OWNER TO postgres;

CREATE TABLE transaction
(
    id               BIGINT,
    account_id       BIGINT                      NOT NULL,
    transaction_type VARCHAR(10)                 NOT NULL,
    balance          NUMERIC                     NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account (id)
);
ALTER TABLE transaction
    OWNER TO postgres;