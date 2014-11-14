-- // update_msc_menu_items
-- Migration SQL that makes the change goes here.

UPDATE menu_items SET state = 'processes.list' WHERE state = 'processes';
UPDATE menu_items SET state = 'schemas.list' WHERE state = 'schemas';
UPDATE menu_items SET state = 'hosts.list' WHERE state = 'hosts';

-- //@UNDO
-- SQL to undo the change goes here.


