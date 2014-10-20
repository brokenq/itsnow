-- // insert_msc_menu_items
-- Migration SQL that makes the change goes here.

INSERT INTO menu_items(id, parent_id, name, state, position, css) VALUES
(1, null, '帐户管理',   'accounts',          0, 'icon-globe'),
(2, null, '合同管理',   'contracts',         1, 'icon-th-list'),
(3, null, '服务管理',   'services',          2, 'icon-tag'),
(4, null, '系统管理',   'system',            3, 'icon-tag'),

(11,   1, '企业帐户',   'accounts.msu',      0, 'icon-tag'), 
(12,   1, '服务商帐户', 'accounts.msp',      1, 'icon-tag'), 
(13,   1, '主机资源',   'hosts',             2, 'icon-cloud'),
(14,   1, '服务进程',   'processes',         3, 'icon-play'),
(15,   1, 'Schema资源', 'schemas',           4, 'icon-play'),

(21,   2, '合同管理',   'contracts.contract',3, 'icon-tag'),

(31,   3, '服务目录',   'services.catalog',  0, 'icon-tag'),
(32,   3, 'SLA管理',    'services.sla',      1, 'icon-eye-open'),

(41,   4, '角色管理',   'system.role',       0, 'icon-group'),
(42,   4, '用户管理',   'system.user',       1, 'icon-user'),
(43,   4, '权限管理',   'system.privilege',  2, 'icon-authenticate');
-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE menu_items;
