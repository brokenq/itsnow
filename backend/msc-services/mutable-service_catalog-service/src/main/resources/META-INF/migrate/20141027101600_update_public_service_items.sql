-- // create_public_service_items
-- Migration SQL that makes the change goes here.

ALTER TABLE public_service_items
ADD COLUMN sn VARCHAR(20) NOT NULL AFTER catalog_id;


-- //@UNDO
-- SQL to undo the change goes here.


DROP TABLE public_service_items IF EXISTS;