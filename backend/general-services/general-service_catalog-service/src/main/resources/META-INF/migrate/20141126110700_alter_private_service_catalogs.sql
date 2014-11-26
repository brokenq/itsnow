-- // alter private service catalogs table
-- Migration SQL that makes the change goes here.

ALTER TABLE private_service_catalogs
DROP FOREIGN KEY private_service_catalogs_ibfk_1;
ALTER TABLE private_service_catalogs
ADD CONSTRAINT private_service_catalogs_ibfk_1
  FOREIGN KEY (parent_id)
  REFERENCES private_service_catalogs (id)
  ON DELETE CASCADE;



-- //@UNDO
-- SQL to undo the change goes here.

