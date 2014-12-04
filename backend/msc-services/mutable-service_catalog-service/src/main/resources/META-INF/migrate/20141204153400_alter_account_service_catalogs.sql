-- // alter_account_service_items
-- Migration SQL that makes the change goes here.


ALTER TABLE itsnow_msc.account_service_catalogs 
ADD UNIQUE INDEX uni_account_service_catalogs (account_id ASC, catalog_id ASC);



-- //@UNDO
-- SQL to undo the change goes here.
