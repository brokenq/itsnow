-- // create_ci_relation_types
-- Migration SQL that makes the change goes here.

CREATE TABLE ci_relation_types (
  id               INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name             VARCHAR(50)   NOT NULL,
  reverse_name     VARCHAR(50)   NOT NULL,
  source_filter    VARCHAR(255),
  dest_filter      VARCHAR(255),
  css              VARCHAR(100),
  description      VARCHAR(255),
  created_at       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE ci_relation_types;

