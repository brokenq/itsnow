-- // insert_menu_items.sql
-- Migration SQL that makes the change goes here.

INSERT INTO menu_items(id, parent_id, name, state, url, template_url, position) VALUES
(1, null, 'Trouble Ticket', 'index.list-trouble-ticket', '/trouble-ticket', 'incident/list-trouble-ticket.tpl.html', 0);

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE menu_items;
