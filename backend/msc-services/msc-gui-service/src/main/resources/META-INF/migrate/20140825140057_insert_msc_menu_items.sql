-- // insert_msc_menu_items
-- Migration SQL that makes the change goes here.

INSERT INTO menu_items(id, parent_id, name, state, url, template_url, position) VALUES
(1, null, '用户',     'index.user',                       '/user',                  'user/list-user.tpl.html',          0),
(2, null, 'SLA',      'index.sla',                        '/sla',                   'sla/list-sla.tpl.html',            2),
(3, null, '合同',     'index.contract',                   '/contract',              'contract/list-contract.tpl.html',1),
(4, null, '故障列表', 'index.list-trouble-ticket',      '/list-trouble-ticket', 'trouble-ticket/list-trouble-ticket.tpl.html', 3),
(5,     4, 'MSC故障', 'index.list-trouble-ticket',      '/list-trouble-ticket', 'trouble-ticket/list-trouble-ticket.tpl.html',0),
(6,     4, 'MSP故障', 'index.msp-list-trouble-ticket', '/list-trouble-ticket', 'trouble-ticket/msp/list-trouble-ticket.tpl.html',1);

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE menu_items;
