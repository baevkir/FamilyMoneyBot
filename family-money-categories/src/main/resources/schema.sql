CREATE TABLE IF NOT EXISTS payment_categories
(
    id   SERIAL       NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_payment_categories
(
    id                  SERIAL                                 NOT NULL UNIQUE,
    user_id             INT                                    NOT NULL,
    payment_category_id INT REFERENCES payment_categories (id) NOT NULL,
    PRIMARY KEY (id)
);


