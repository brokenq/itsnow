-- // alter_account_service_items
-- Migration SQL that makes the change goes here.


ALTER TABLE itsnow_msc.account_service_items
ADD UNIQUE INDEX uni_account_service_items (account_id ASC, item_id ASC);



-- //@UNDO
-- SQL to undo the change goes here.
