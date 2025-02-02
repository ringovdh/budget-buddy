CREATE TABLE IF NOT EXISTS project (
    id              SERIAL PRIMARY KEY AUTO_INCREMENT,
    projectName     VARCHAR(255),
    description     VARCHAR(255),
    startDate       DATE,
    endDate         DATE,
    icon            VARCHAR(100)
)