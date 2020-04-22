CREATE TABLE bot_users
(
    id          SERIAL       NOT NULL UNIQUE,
    telegram_id INT          NOT NULL UNIQUE,
    user_name   VARCHAR(255) NOT NULL,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE accounts
(
    id      SERIAL       NOT NULL UNIQUE,
    chat_id INT          NOT NULL,
    name    VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE payment_categories
(
    id      SERIAL       NOT NULL UNIQUE,
    chat_id INT          NOT NULL,
    name    VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE payments
(
    id                  SERIAL                                 NOT NULL UNIQUE,
    chat_id             INT                                    NOT NULL,
    user_id             INT REFERENCES bot_users (id)          NOT NULL,
    account_id          INT REFERENCES accounts (id)           NOT NULL,
    payment_category_id INT REFERENCES payment_categories (id) NOT NULL,
    amount              DECIMAL                                NOT NULL,
    date                TIMESTAMP                              NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE incomes
(
    id         SERIAL                        NOT NULL UNIQUE,
    chat_id    INT                           NOT NULL,
    user_id    INT REFERENCES bot_users (id) NOT NULL,
    account_id INT REFERENCES accounts (id)  NOT NULL,
    amount     DECIMAL                       NOT NULL,
    date       TIMESTAMP                     NOT NULL,
    PRIMARY KEY (id)
);

