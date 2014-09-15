-- // create_authorities
-- Migration SQL that makes the change goes here.

CREATE TABLE authorities (
    username  VARCHAR(50)  NOT NULL,
    authority VARCHAR(255) NOT NULL,
    FOREIGN key (authority) REFERENCES roles(name)
);
CREATE UNIQUE INDEX idx_authorities_username ON authorities(username, authority);


-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE authorities;

