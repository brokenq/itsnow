-- // insert_msc_menu_items
-- Migration SQL that makes the change goes here.

INSERT INTO menu_items(id, parent_id, name, state, url, template_url, position) VALUES
(1, null, 'User',             'index.user',      '/user',      'user/list-user.tpl.html',         0),
(2, null, 'SLA',              'index.sla',       '/sla',       'sla/list-sla.tpl.html',            1),
(3, null, 'Contract', 'index.contract', '/contract', 'contract/list-contract.tpl.html', 2);

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE menu_items;
