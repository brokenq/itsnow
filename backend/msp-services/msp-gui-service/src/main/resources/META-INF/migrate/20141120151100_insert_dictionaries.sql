
-- // insert_dictionaries
-- Migration SQL that makes the change goes here.


INSERT INTO dictionaries(code, name, label, description, detail ) VALUES
('001', '字典名称一', '北京', '描述一', '[{"key":"k11","value":"v11"},{"key":"k11","value":"v11"},{"key":"k11","value":"v11"}]'),
('002', '字典名称二', '上海', '描述二', '[{"key":"k22","value":"v22"},{"key":"k22","value":"v22"},{"key":"k22","value":"v22"}]'),
('003', '字典名称三', '广州', '描述三', '[{"key":"k33","value":"v33"},{"key":"k33","value":"v33"},{"key":"k33","value":"v33"}]');




-- INSERT INTO dictionaries (sn,code,name,display,val,state,type) VALUES
--     ('111','inc001','优先级','高','high','1','1'),
--     ('112','inc001','优先级','中','middle','1','1'),
--     ('113','inc001','优先级','低','low','1','1'),
--     ('114','inc002','紧急程度','高','high','1','1'),
--     ('115','inc002','紧急程度','中','middle','1','1'),
--     ('116','inc002','紧急程度','低','low','1','1'),
--     ('117','inc004','影响程度','高','high','1','1'),
--     ('118','inc004','影响程度','中','middle','1','1'),
--     ('119','inc004','影响程度','低','low','1','1'),
--     ('120','inc005','请求类型','电话','phone','1','1'),
--     ('121','inc005','请求类型','邮件','email','1','1'),
--     ('122','inc005','请求类型','网站','web','1','1');

-- //@UNDO
-- SQL to undo the change goes here.