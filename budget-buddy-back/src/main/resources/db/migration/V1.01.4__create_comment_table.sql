CREATE TABLE IF NOT EXISTS comments (
    id              SERIAL          PRIMARY KEY AUTO_INCREMENT,
    searchterm      VARCHAR(255)    NOT NULL,
    replacement     VARCHAR(255)    NOT NULL,
    category_id     INT REFERENCES category (id)
)