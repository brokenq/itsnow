-- // create_authorities
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS authorities (
    username  VARCHAR(50)  NOT NULL,
    authority VARCHAR(255) NOT NULL
--     FOREIGN key (authority) REFERENCES roles(name)
);

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE IF EXISTS authorities;

