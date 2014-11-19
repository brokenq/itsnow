-- // update_msc_menu_items
-- Migration SQL that makes the change goes here.

UPDATE menu_items SET state = 'catalog.list'
WHERE state = 'services.catalog';

-- //@UNDO
-- SQL to undo the change goes here.