-- // create_acl_classes
-- Migration SQL that makes the change goes here.

CREATE TABLE acl_classes (
  id         INT(4) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  class      VARCHAR(100)    NOT NULL,
  created_at TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_acl_classes (class)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE acl_classes;
