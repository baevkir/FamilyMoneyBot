CREATE TABLE bot_users
(
    id          SERIAL       NOT NULL UNIQUE,
    telegram_id INT          NOT NULL UNIQUE,
    user_name   VARCHAR(255) NOT NULL,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE payment_type
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
