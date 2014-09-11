-- // create_process_dictionary
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS public_service_catalogs (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  parent_id int(10) unsigned DEFAULT NULL,
  sn varchar(20) NOT NULL,
  title varchar(255) NOT NULL,
  description tinytext,
  icon varchar(100) DEFAULT NULL,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY parent_id (parent_id)
--   CONSTRAINT public_service_catalogs_ibfk_1 FOREIGN KEY (parent_id) REFERENCES public_service_catalogs (id)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS public_service_catalogs;
