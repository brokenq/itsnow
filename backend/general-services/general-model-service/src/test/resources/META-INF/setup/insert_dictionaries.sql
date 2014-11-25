-- // insert_dictionaries
-- Migration SQL that makes the change goes here.

--
-- INSERT INTO dictionaries(code, name, label, description, detail ) VALUES
-- ('001', '字典名称一', '北京', '描述一', '[{"key":"k11","value":"v11"},{"key":"k11","value":"v11"},{"key":"k11","value":"v11"}]'),
-- ('002', '字典名称二', '上海', '描述二', '[{"key":"k22","value":"v22"},{"key":"k22","value":"v22"},{"key":"k22","value":"v22"}]'),
-- ('003', '字典名称三', '广州', '描述三', '[{"key":"k33","value":"v33"},{"key":"k33","value":"v33"},{"key":"k33","value":"v33"}]');










INSERT INTO dictionaries(sn, code, name, display, val, state, type) VALUES
('001', 'inc001', '优先程度', '高 ',  'high',    '1', '1'),
('002', 'inc001', '优先程度', '中 ',  'middle',  '1', '1'),
('003', 'inc001', '优先程度', '低 ',  'low',     '1', '1'),
('004', 'inc002', '影响程度', '高 ',  'high',    '1', '1'),
('005', 'inc002', '影响程度', '中 ',  'middle',  '1', '1'),
('006', 'inc002', '影响程度', '低 ',  'low',     '1', '1');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM dictionaries;
