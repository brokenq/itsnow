-- // update_menu_items
-- Migration SQL that makes the change goes here.

update menu_items set state = 'roles.list' where state = 'role';
update menu_items set state = 'contracts.list' where state = 'contracts.contract';

-- //@UNDO
-- SQL to undo the change goes here.


