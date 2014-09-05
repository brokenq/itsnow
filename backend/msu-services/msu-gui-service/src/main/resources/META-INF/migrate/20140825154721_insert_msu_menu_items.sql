-- // insert_menu_items.sql
-- Migration SQL that makes the change goes here.

INSERT INTO menu_items(id, parent_id, name, state, url, template_url, position) VALUES
(1, null, '系统配置', 'index.system','',  '',   0),
(3, null, '服务管理', 'index.services','',  '',   1),
(2, null, '合同管理',  'index.contracts','',  '',   2),
(4, null, '故障管理',  'index.incidents','',  '',   3),

(11, 1, '角色管理', 'index.system.role',       '/system-role',      'system/list-role.tpl.html',        0),
(12, 1, '用户管理', 'index.system.user',       '/system-user',      'system/list-user.tpl.html',        1),
(13, 1, '权限管理', 'index.system.privilege', '/system-privilege','system/list-privilege.tpl.html',   2),
(14, 1, '部门管理', 'index.system.department','/system-department','system/tree-department.tpl.html',  3),
(15, 1, '工作时间', 'index.system.worktime',   '/system-worktime', 'system/list-worktime.tpl.html',    4),
(16, 1, '地点管理', 'index.system.site',       '/system-site',      'system/list-site.tpl.html',         5),
(17, 1, '员工管理', 'index.system.staff',      '/system-staff',     'system/list-staff.tpl.html',       6),
(18, 1, '组管理',   'index.system.group',      '/system-group',     'system/list-group.tpl.html',       7),

(21, 2, '合同管理', 'index.contracts.contract','/contracts-contract','contracts/list-contract.tpl.html', 0),

(31, 3, '服务目录', 'index.services.catalog','/services-catalog',  'service/list-catalog.tpl.html', 0),
(32, 3, 'SLA管理', 'index.services.sla',      '/services-sla',      'service/list-sla.tpl.html', 1),
(33, 3, '字典管理', 'index.services.dict',      '/services-dict',   'service/list-dict.tpl.html', 1),
(34, 3, '流程管理', 'index.services.workflow', '/services-workflow',   'service/list-workflow.tpl.html', 1),

(41, 4, '新建故障',   'index.incidents.create', '/incidents-create',   'incidents/create.tpl.html', 1),
(42, 4, '我的故障单', 'index.incidents.my',     '/incidents-my',        'incidents/list-my.tpl.html', 2),
(43, 4, '已关闭故障', 'index.incidents.closed', '/incidents-closed',   'incidents/list-closed.tpl.html', 3);

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE menu_items;
