-- // create_groups
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS groups (
   id          INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
   sn          VARCHAR(10)    NOT NULL,
   group_name  VARCHAR(255)    NOT NULL,
   description VARCHAR(500),
   created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS groups;
