-- // insert_dictionaries
-- Migration SQL that makes the change goes here.
DELETE FROM act_re_procdef;
INSERT INTO act_re_procdef
(id_, rev_, category_, name_, key_, version_, deployment_id_, description_, tenant_id_) VALUES
('1', 1, '类别一', '引擎一',  '100',  1, '1', 'it is test.', '1'),
('2', 1, '类别二', '引擎二',  '100',  1, '2', 'it is test.', '2'),
('3', 1, '类别三', '引擎三',  '100',  1, '3', 'it is test.', '3'),
('4', 1, '类别四', '引擎四',  '100',  1, '4', 'it is test.', '4'),
('5', 1, '类别五', '引擎五',  '100',  1, '5', 'it is test.', '5'),
('6', 1, '类别六', '引擎六',  '100',  1, '6', 'it is test.', '6');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM act_re_procdef;
