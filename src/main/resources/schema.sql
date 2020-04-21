CREATE TABLE bot_users
(
    id          SERIAL       NOT NULL UNIQUE,
    telegram_id INT          NOT NULL UNIQUE,
    user_name   VARCHAR(255) NOT NULL,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
