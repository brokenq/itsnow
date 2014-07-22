-- // create_private_service_catalogs
-- Migration SQL that makes the change goes here.

CREATE TABLE private_service_catalogs (
  id          INT(4) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  parent_id   INT(4) UNSIGNED NOT NULL,
  parent_type VARCHAR (10)    NOT NULL,
  title       VARCHAR(255)    NOT NULL,
  description TEXT(16),
  icon        VARCHAR(100),
  created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE private_service_catalogs;
