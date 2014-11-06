-- // insert_menu_items.sql
-- Migration SQL that makes the change goes here.

INSERT INTO menu_items(id, parent_id, name, state, position, css) VALUES
(1, null, '系统配置', 'system',0, 'icon-tag'),
(3, null, '服务管理', 'services',1, 'icon-tag'),
(2, null, '合同管理',  'contracts',2, 'icon-th-list'),
(4, null, '故障管理',  'incidents',3, 'icon-warning-sign'),

(11, 1, '角色管理', 'role', 0, 'icon-group'),
(12, 1, '用户管理', 'user', 1, 'icon-user'),
(13, 1, '权限管理', 'privilege', 2, 'icon-authenticate'),
(14, 1, '部门管理', 'department',3, 'icon-th-list'),
(15, 1, '工作时间', 'worktime',  4, 'icon-time'),
(16, 1, '地点管理', 'site',      5, 'icon-home'),
(17, 1, '员工管理', 'staff',     6, 'icon-user'),
(18, 1, '组管理',   'group',     7, 'icon-th-large'),

(21, 2, '邀约管理', 'contracts.list',0, 'icon-tag'),
(22, 2, '我的合约', 'contracts.my_list',1, 'icon-tag'),

(31, 3, '服务目录', 'services.catalog', 0, 'icon-th'),
(32, 3, 'SLA管理', 'slas.list',      1, 'icon-eye-open'),
(33, 3, '字典管理', 'dict',    1, 'icon-book'),
(34, 3, '流程管理', 'workflow',1, 'icon-task'),

(41, 4, '新建故障',   'incidents-create', 1, 'icon-fire'),
(42, 4, '我的故障单', 'incidents-opened',     2, 'icon-eye-open'),
(43, 4, '已关闭故障', 'incidents-closed', 3, 'icon-eye-close');
-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE menu_items;
