CREATE TABLE IF NOT EXISTS bot_users
(
    id          SERIAL       NOT NULL UNIQUE,
    telegram_id INT          NOT NULL UNIQUE,
    user_name  VARCHAR(255),
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS accounts
(
    id   SERIAL       NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_accounts
(
    id   SERIAL       NOT NULL UNIQUE,
    user_id    INT REFERENCES bot_users (id) NOT NULL,
    account_id INT REFERENCES accounts (id)  NOT NULL,
    PRIMARY KEY (user_id, account_id)
);

CREATE TABLE IF NOT EXISTS  payment_categories
(
    id   SERIAL       NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_payment_categories
(
    id                  SERIAL                                 NOT NULL UNIQUE,
    user_id             INT REFERENCES bot_users (id)          NOT NULL,
    payment_category_id INT REFERENCES payment_categories (id) NOT NULL,
    PRIMARY KEY (user_id, payment_category_id)
);

CREATE TABLE IF NOT EXISTS payments
(
    id                  SERIAL                                 NOT NULL UNIQUE,
    user_id             INT REFERENCES bot_users (id)          NOT NULL,
    account_id          INT REFERENCES accounts (id)           NOT NULL,
    payment_category_id INT REFERENCES payment_categories (id) NOT NULL,
    amount              DECIMAL                                NOT NULL,
    date                TIMESTAMP                              NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS incomes
(
    id         SERIAL                        NOT NULL UNIQUE,
    user_id    INT REFERENCES bot_users (id) NOT NULL,
    account_id INT REFERENCES accounts (id)  NOT NULL,
    amount     DECIMAL                       NOT NULL,
    date       TIMESTAMP                     NOT NULL,
    PRIMARY KEY (id)
);

