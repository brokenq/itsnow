-- // insert_menu_items.sql
-- Migration SQL that makes the change goes here.

INSERT INTO menu_items(id, parent_id, name, state, url, template_url, position) VALUES
(1, null, '用户',   'index.user',                   '/user',                  'user/list-user.tpl.html',                0),
(2, null, 'SLA',    'index.sla',                    '/sla',                   'sla/list-sla.tpl.html',                   1),
(3, null, '合同',   'index.contract',              '/contract',             'contract/list-contract.tpl.html',        2),
(4, null, '故障单', 'index.list-trouble-ticket', '/trouble-ticket',      'incident/list-trouble-ticket.tpl.html', 3);

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE menu_items;
