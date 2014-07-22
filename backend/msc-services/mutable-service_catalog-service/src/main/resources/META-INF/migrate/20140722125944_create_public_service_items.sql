-- // create_public_service_items
-- Migration SQL that makes the change goes here.

CREATE TABLE public_service_items (
  id          INT(4) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  catalog_id  INT(4) UNSIGNED NOT NULL,
  title       VARCHAR(255)    NOT NULL,
  brief       VARCHAR(255),
  description TEXT(16),
  icon        VARCHAR(100),
  created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (catalog_id) REFERENCES public_service_catalogs(id)
);


-- //@UNDO
-- SQL to undo the change goes here.


DROP TABLE public_service_items;