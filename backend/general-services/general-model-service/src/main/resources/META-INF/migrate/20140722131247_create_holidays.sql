-- // create_holidays
-- Migration SQL that makes the change goes here.

CREATE TABLE holidays (
   id           INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
   name         VARCHAR(255)    NOT NULL,
   rule         VARCHAR(255)    NOT NULL,
   started_at  VARCHAR(10)     NOT NULL,
   ended_at    VARCHAR(10)      NOT NULL,
   description VARCHAR(255),
   created_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE holidays;
