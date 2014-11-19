-- // update_menu_items
-- Migration SQL that makes the change goes here.

update menu_items set state = 'roles.list' where state = 'role';
update menu_items set state = 'departments.list' where state = 'department';
update menu_items set state = 'sites.list' where state = 'site';
update menu_items set state = 'staffs.list' where state = 'staff';
update menu_items set state = 'contracts.list' where state = 'contracts.contract';
update menu_items set state = 'workflows.list' where state = 'workflow';

-- //@UNDO
-- SQL to undo the change goes here.


