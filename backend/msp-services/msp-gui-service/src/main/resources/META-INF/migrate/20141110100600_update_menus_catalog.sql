-- // update item_menus
-- Migration SQL that makes the change goes here.

UPDATE menu_items SET state = 'catalog.list'
WHERE state = 'services.catalog';

UPDATE menu_items SET state = 'incidents.opened'
WHERE state = 'incidents-create';

UPDATE menu_items SET state = 'incidents.closed'
WHERE state = 'incidents-closed';

UPDATE menu_items SET state = 'incidents.create'
WHERE state = 'incidents-create';

-- //@UNDO
-- SQL to undo the change goes here.


