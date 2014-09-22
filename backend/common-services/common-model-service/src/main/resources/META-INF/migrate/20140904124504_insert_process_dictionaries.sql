-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO process_dictionaries(code, name, level, level_name, state, type) VALUES
('inc001', '优先程度',   'high',   '高',   '1', '1'),
('inc002', '优先程度',   'middle', '中',   '1', '1'),
('inc003', '优先程度',   'low',    '低',   '1', '1'),
('inc004', '影响程度',   'high',   '高',   '1', '1'),
('inc005', '影响程度',   'middle', '中',   '1', '1'),
('inc006', '影响程度',   'low',    '低',   '1', '1'),
('inc007', '北京',       'normal', '一般', '1', '1'),
('inc008', '上海',       'normal', '一般', '1', '1');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM IF EXISTS process_dictionaries;
