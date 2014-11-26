-- // alter public service catalogs table
-- Migration SQL that makes the change goes here.

ALTER TABLE public_service_catalogs
DROP FOREIGN KEY public_service_catalogs_ibfk_1;
ALTER TABLE public_service_catalogs
ADD CONSTRAINT public_service_catalogs_ibfk_1
  FOREIGN KEY (parent_id)
  REFERENCES public_service_catalogs (id)
  ON DELETE CASCADE;



-- //@UNDO
-- SQL to undo the change goes here.

