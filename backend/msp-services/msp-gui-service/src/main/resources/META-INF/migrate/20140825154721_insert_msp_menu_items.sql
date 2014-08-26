-- // insert_menu_items.sql
-- Migration SQL that makes the change goes here.

INSERT INTO menu_items(parent_id, name, type, url) VALUES
(null, '用户', '0',  'index.user'),
(null, 'SLA', '0', 'index.sla'),
(null, '合同', '0','index.contract'),
(null, '故障单', '0','index.msp-list-trouble-ticket');

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE menu_items;
