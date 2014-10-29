-- // create_group_authorities
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS group_authorities (
    id        INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    group_id  INT(10) UNSIGNED  NOT NULL,
    authority VARCHAR(255)     NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups (id),
    FOREIGN KEY (authority) REFERENCES roles (name)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS group_authorities;
