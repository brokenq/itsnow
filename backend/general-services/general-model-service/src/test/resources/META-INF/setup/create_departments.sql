-- // create_departments
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS departments
(
   id           INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
   sn           VARCHAR(100)      NOT NULL,
   name         VARCHAR(255)    NOT NULL,
   parent_id   INT(10) UNSIGNED,
   position     INT(10) UNSIGNED,
   description VARCHAR(255),
   created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE departments;

