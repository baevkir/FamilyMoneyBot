CREATE TABLE IF NOT EXISTS bot_users
(
    id          SERIAL       NOT NULL UNIQUE,
    telegram_id INT          NOT NULL UNIQUE,
    user_name  VARCHAR(255),
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    PRIMARY KEY (id)
);


