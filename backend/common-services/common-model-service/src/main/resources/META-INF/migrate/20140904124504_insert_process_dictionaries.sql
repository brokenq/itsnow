-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO process_dictionaries(sn, code, name, display, val, state, type) VALUES
('001', 'inc001', '优先级',    '高',         'high',    '1', '1'),
('002', 'inc001', '优先级',    '中',         'middle',  '1', '1'),
('003', 'inc001', '优先级',    '低',         'low',     '1', '1'),
('004', 'inc002', '影响程度',  '高',         'high',    '1', '1'),
('005', 'inc002', '影响程度',  '中',         'middle',  '1', '1'),
('006', 'inc002', '影响程度',  '低',         'low',     '1', '1'),
('007', 'inc003', '地理位置',  '上海',       'sh',       '1', '1'),
('008', 'inc003', '地理位置',  '北京',       'bj',       '1', '1'),
('009', 'inc004', '流程',      'INCIDENT',   'flow',    '1', '1'),
('010', 'inc004', '流程',      'PROBLEM',    'flow',    '1', '1');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM IF EXISTS process_dictionaries;
