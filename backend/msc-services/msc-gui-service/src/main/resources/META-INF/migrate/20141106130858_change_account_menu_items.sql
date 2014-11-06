-- // change_account_menu_items
-- Migration SQL that makes the change goes here.

update menu_items set state = 'accounts.msu_list' where state = 'accounts.msu';
update menu_items set state = 'accounts.msp_list' where state = 'accounts.msp';

-- //@UNDO
-- SQL to undo the change goes here.

update menu_items set state = 'accounts.msu' where state = 'accounts.msu_list';
update menu_items set state = 'accounts.msp' where state = 'accounts.msp_list';

