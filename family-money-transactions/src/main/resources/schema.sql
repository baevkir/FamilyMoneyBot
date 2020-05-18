CREATE TABLE IF NOT EXISTS payments
(
    id                  SERIAL    NOT NULL UNIQUE,
    user_id             INT       NOT NULL,
    account_id          INT       NOT NULL,
    payment_category_id INT       NOT NULL,
    amount              DECIMAL   NOT NULL,
    date                TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS incomes
(
    id         SERIAL    NOT NULL UNIQUE,
    user_id    INT       NOT NULL,
    account_id INT       NOT NULL,
    amount     DECIMAL   NOT NULL,
    date       TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

