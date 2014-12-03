-- // alter_dictionaries
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS dictionaries (
  id           INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  code         VARCHAR(255)     NOT NULL,
  name         VARCHAR(255)     NOT NULL,
  label        VARCHAR(255)     NOT NULL,
  description  VARCHAR(500),
  detail       TEXT             NOT NULL,
  created_at   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS dictionaries;