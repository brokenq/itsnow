-- // create_public_service_catalogs
-- Migration SQL that makes the change goes here.

CREATE TABLE public_service_catalogs (
  id          INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  parent_id   INT(10) UNSIGNED,
  sn          VARCHAR(20)      NOT NULL,
  title       VARCHAR(255)     NOT NULL,
  description TEXT(16),
  icon        VARCHAR(100),
  level       INT(10) UNSIGNED NOT NULL DEFAULT 1,
  created_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (parent_id)      REFERENCES public_service_catalogs(id)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE public_service_catalogs;
