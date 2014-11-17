-- // update_menu_items
-- Migration SQL that makes the change goes here.
UPDATE menu_items SET state = 'dicts.list' WHERE id = 33;
UPDATE menu_items SET state = 'users.list' WHERE id =42;
-- //@UNDO
-- SQL to undo the change goes here.


