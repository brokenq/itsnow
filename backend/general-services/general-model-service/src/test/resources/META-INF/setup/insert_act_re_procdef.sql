-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.
DELETE FROM act_re_procdef;
INSERT INTO act_re_procdef
(id_, rev_, category_, name_, key_, version_, deployment_id_, description_, tenant_id_) VALUES
('1', 1, 'category1', 'name1',  '100',  1, '1', 'it is test.', '1'),
('2', 1, 'category2', 'name2',  '100',  1, '2', 'it is test.', '2'),
('3', 1, 'category3', 'name3',  '100',  1, '3', 'it is test.', '3'),
('4', 1, 'category4', 'name4',  '100',  1, '4', 'it is test.', '4'),
('5', 1, 'category5', 'name5',  '100',  1, '5', 'it is test.', '5'),
('6', 1, 'category6', 'name6',  '100',  1, '6', 'it is test.', '6');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM act_re_procdef;
