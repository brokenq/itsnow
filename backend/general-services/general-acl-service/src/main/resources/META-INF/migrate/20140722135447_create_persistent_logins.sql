-- // create_persistent_logins
-- Migration SQL that makes the change goes here.

CREATE TABLE persistent_logins (
  id         INT(4) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username   VARCHAR(64)     NOT NULL,
  series     VARCHAR(64)     NOT NULL,
  token      VARCHAR(64)     NOT NULL,
  last_used  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_at TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_persistent_logins_serial (series)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE persistent_logins;
