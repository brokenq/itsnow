-- // create_group_authorities
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS group_roles (
    id         INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    group_id   INT(10) UNSIGNED  NOT NULL,
    role_id    VARCHAR(255)     NOT NULL
--     FOREIGN KEY (group_id) REFERENCES groups (id),
--     FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS group_roles;
