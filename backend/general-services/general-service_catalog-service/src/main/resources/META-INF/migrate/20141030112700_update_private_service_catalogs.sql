-- // create_public_service_items
-- Migration SQL that makes the change goes here.

ALTER TABLE private_service_catalogs
ADD COLUMN level INT(10) NOT NULL DEFAULT 1 AFTER icon;


-- //@UNDO
-- SQL to undo the change goes here.


DROP TABLE private_service_items IF EXISTS;