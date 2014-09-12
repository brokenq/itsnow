-- // create_roles
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS roles
(
   id           INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
   name         VARCHAR(255)    NOT NULL UNIQUE,
   description VARCHAR(500),
   created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS roles;
