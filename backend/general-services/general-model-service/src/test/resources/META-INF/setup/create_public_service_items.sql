-- // create_process_dictionary
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS public_service_items (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  catalog_id int(10) unsigned NOT NULL,
  title varchar(255) NOT NULL,
  brief varchar(255) DEFAULT NULL,
  description tinytext,
  icon varchar(100) DEFAULT NULL,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY catalog_id (catalog_id)
--   CONSTRAINT public_service_items_ibfk_1 FOREIGN KEY (catalog_id) REFERENCES public_service_catalogs (id)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS public_service_items;
