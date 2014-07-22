-- // create_private_service_items
-- Migration SQL that makes the change goes here.

CREATE TABLE private_service_items (
  id           INT(4) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  catalog_id   INT(4) UNSIGNED NOT NULL,
  catalog_type VARCHAR(20)     NOT NULL DEFAULT 'public',
  title        VARCHAR(255)    NOT NULL,
  brief        VARCHAR(255),
  description  TEXT(16),
  icon         VARCHAR(100),
  created_at   TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at   TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);



-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE private_service_items;
