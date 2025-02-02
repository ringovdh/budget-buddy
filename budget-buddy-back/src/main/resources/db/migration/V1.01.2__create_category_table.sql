CREATE TABLE IF NOT EXISTS category (
    id              SERIAL          PRIMARY KEY AUTO_INCREMENT,
    label           VARCHAR(255)    NOT NULL,
    icon            VARCHAR(100),
    fixedCost       BOOLEAN         DEFAULT FALSE,
    inDetails       BOOLEAN         DEFAULT FALSE,
    inMonitor       BOOLEAN         DEFAULT FALSE,
    limitAmount     DOUBLE(8,2)     DEFAULT 0,
    revenue         BOOLEAN         DEFAULT FALSE,
    saving          BOOLEAN         DEFAULT FALSE
)