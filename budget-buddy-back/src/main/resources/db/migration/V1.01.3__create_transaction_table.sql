CREATE TABLE IF NOT EXISTS transaction (
    tx_id           SERIAL          PRIMARY KEY AUTO_INCREMENT,
    number          VARCHAR(255)    NOT NULL,
    amount          DOUBLE(8,2)     NOT NULL,
    sign            VARCHAR(1)      NOT NULL,
    date            DATE,
    comment         VARCHAR(255),
    category        INT REFERENCES category (id),
    project_id      INT REFERENCES project (id)
)