-- // create_slas
-- Migration SQL that makes the change goes here.

CREATE TABLE slas (
  id          INT(4) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  title       VARCHAR(255)    NOT NULL,
  description TEXT(16),
  created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);



-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE slas;
