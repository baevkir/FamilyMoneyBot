CREATE TABLE bot_users
(
    id          SERIAL       NOT NULL UNIQUE,
    telegram_id INT          NOT NULL UNIQUE,
    user_name   VARCHAR(255) NOT NULL,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE account
(
    id      SERIAL       NOT NULL UNIQUE,
    chat_id INT          NOT NULL,
    name    VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE payment_category
(
    id      SERIAL       NOT NULL UNIQUE,
    chat_id INT          NOT NULL,
    name    VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE payment
(
    id                  SERIAL                               NOT NULL UNIQUE,
    chat_id             INT                                  NOT NULL,
    user_id             INT REFERENCES bot_users (id)        NOT NULL,
    payment_type_id     INT REFERENCES account (id)     NOT NULL,
    payment_category_id INT REFERENCES payment_category (id) NOT NULL,
    amount              DECIMAL                              NOT NULL,
    payment_date        TIMESTAMP                            NOT NULL,
    PRIMARY KEY (id)
);
