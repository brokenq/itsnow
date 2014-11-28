-- // alter public service items table
-- Migration SQL that makes the change goes here.

ALTER TABLE public_service_items
DROP FOREIGN KEY public_service_items_ibfk_1;
ALTER TABLE public_service_items
ADD CONSTRAINT public_service_items_ibfk_1
  FOREIGN KEY (catalog_id)
  REFERENCES public_service_catalogs (id)
  ON DELETE CASCADE;


-- //@UNDO
-- SQL to undo the change goes here.
