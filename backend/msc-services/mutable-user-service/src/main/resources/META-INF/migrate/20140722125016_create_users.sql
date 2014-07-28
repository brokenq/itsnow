-- // create_users
-- Migration SQL that makes the change goes here.

CREATE TABLE users (
  id         INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username   VARCHAR(25)      NOT NULL,
  email      VARCHAR(255),
  phone      VARCHAR(50),
  password   VARCHAR(255)     NOT NULL,
  enabled    BOOLEAN          NOT NULL DEFAULT TRUE,
  expired    BOOLEAN          NOT NULL DEFAULT FALSE,
  created_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE users;
