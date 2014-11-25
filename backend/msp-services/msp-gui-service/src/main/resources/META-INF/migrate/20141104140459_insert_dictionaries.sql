-- // insert_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO dictionaries(code, name, label, description, detail ) VALUES
('001', '字典名称一', '北京', '描述一', '[{"key":"k11","value":"v11"},{"key":"k11","value":"v11"},{"key":"k11","value":"v11"}]'),
('002', '字典名称二', '上海', '描述二', '[{"key":"k22","value":"v22"},{"key":"k22","value":"v22"},{"key":"k22","value":"v22"}]'),
('003', '字典名称三', '广州', '描述三', '[{"key":"k33","value":"v33"},{"key":"k33","value":"v33"},{"key":"k33","value":"v33"}]');





-- INSERT INTO dictionaries(sn, code, name, display, val, state, type) VALUES
--   ('001', 'inc003', '区域', '北京', '11', '1', '1'),
--   ('002', 'inc003', '区域', '天津', '12', '1', '1'),
--   ('003', 'inc003', '区域', '上海', '31', '1', '1'),
--   ('004', 'inc003', '区域', '重庆', '50', '1', '1');

-- //@UNDO
-- SQL to undo the change goes here.


