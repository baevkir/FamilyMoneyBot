CREATE TABLE IF NOT EXISTS accounts
(
    id   SERIAL       NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_accounts
(
    id         SERIAL NOT NULL UNIQUE,
    user_id    INT    NOT NULL,
    account_id INT    NOT NULL,
    PRIMARY KEY (id)
);




