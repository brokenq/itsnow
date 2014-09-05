-- // insert_msc_menu_items
-- Migration SQL that makes the change goes here.

INSERT INTO menu_items(id, parent_id, name, state, url, template_url, position) VALUES
(1, null, '帐户管理', 'index.accounts',   '',  '',   0),
(2, null, '合同管理',  'index.contracts','',  '',   1),
(3, null, '服务管理', 'index.services','',  '',   2),
(4, null, '系统管理', 'index.system','',  '',   3),
(11, 1, '企业帐户', 'index.accounts.msu',      '/account-msu',      'account/list-msu.tpl.html',      0),
(12, 1, '服务商帐户', 'index.accounts.msp',    '/account-msp',      'account/list-msp.tpl.html',      1),
(13, 1, '服务进程', 'index.accounts.service', '/account-service',  'account/list-service.tpl.html', 2),

(21, 2, '合同管理', 'index.contracts.contract','/contracts-contract','contracts/list-contract.tpl.html', 0),

(31, 3, '服务目录', 'index.services.catalog','/services-catalog',  'service/list-catalog.tpl.html', 0),
(32, 3, 'SLA管理', 'index.services.sla',      '/services-sla',      'service/list-sla.tpl.html', 1),

(41, 4, '角色管理', 'index.system.role',      '/system-role',      'system/list-role.tpl.html',       0),
(42, 4, '用户管理', 'index.system.user',      '/system-user',      'system/list-user.tpl.html',       1),
(43, 4, '权限管理', 'index.system.privilege','/system-privilege','system/list-privilege.tpl.html', 2);
-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE menu_items;
