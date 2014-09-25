-- // create_process_dictionary
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS process_dictionaries (
  id           INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  code         VARCHAR(255)     NOT NULL,
  name         VARCHAR(255)     NOT NULL,
  level        VARCHAR(255)     NOT NULL,
  level_name  VARCHAR(255)     NOT NULL,
  state        VARCHAR(1)       NOT NULL,
  type         VARCHAR(1),
  description VARCHAR(500),
  created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS process_dictionaries;
