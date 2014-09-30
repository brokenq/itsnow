-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.
DELETE FROM process_dictionaries;
INSERT INTO process_dictionaries(sn, code, name, display, val, state, type) VALUES
('001', 'inc001', '优先级',  '高',  'high',   '1', '1'),
('002', 'inc001', '优先级',  '中',  'middle', '1', '1'),
('003', 'inc001', '优先级',  '低',  'low',    '1', '1'),
('004', 'inc002', '影响程度','高',  'high',   '1', '1'),
('005', 'inc002', '影响程度','中',  'middle', '1', '1'),
('006', 'inc002', '影响程度','低',  'low',    '1', '1');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM process_dictionaries;
