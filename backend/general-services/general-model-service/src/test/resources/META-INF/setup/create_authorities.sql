CREATE TABLE IF NOT EXISTS authorities (
    username  VARCHAR(50)  NOT NULL,
    authority VARCHAR(255) NOT NULL
--     FOREIGN key (authority) REFERENCES roles(name)
);

