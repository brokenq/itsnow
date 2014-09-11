-- // insert_menu_items.sql
-- Migration SQL that makes the change goes here.

INSERT INTO menu_items(id, parent_id, name, state, url, template_url, position) VALUES
(1, null, '系统配置', 'index.system',            0),
(3, null, '服务管理', 'index.services',          1),
(2, null, '合同管理',  'index.contracts',        2),
(4, null, '故障管理',  'index.incidents',        3),

(11, 1, '角色管理', 'index.system.role',         0),
(12, 1, '用户管理', 'index.system.user',         1),
(13, 1, '权限管理', 'index.system.privilege',    2),
(14, 1, '部门管理', 'index.system.department',   3),
(15, 1, '工作时间', 'index.system.worktime',     4),
(16, 1, '地点管理', 'index.system.site',         5),
(17, 1, '员工管理', 'index.system.staff',        6),
(18, 1, '组管理',   'index.system.group',        7),

(21, 2, '合同管理', 'index.contracts.contract', 0),

(31, 3, '服务目录', 'index.services.catalog',   0),
(32, 3, 'SLA管理', 'index.services.sla',        1),
(33, 3, '字典管理', 'index.services.dict',      1),
(34, 3, '流程管理', 'index.services.workflow',  1),

(41, 4, '新建故障',   'index.incidents.create', 1),
(42, 4, '我的故障单', 'index.incidents.my',     2),
(43, 4, '已关闭故障', 'index.incidents.closed', 3);

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE menu_items;
