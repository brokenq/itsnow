-- // alter private service items table
-- Migration SQL that makes the change goes here.

ALTER TABLE private_service_items
DROP FOREIGN KEY private_service_items_ibfk_1;
ALTER TABLE private_service_items
ADD CONSTRAINT private_service_items_ibfk_1
  FOREIGN KEY (catalog_id)
  REFERENCES private_service_catalogs (id)
  ON DELETE CASCADE;


-- //@UNDO
-- SQL to undo the change goes here.
