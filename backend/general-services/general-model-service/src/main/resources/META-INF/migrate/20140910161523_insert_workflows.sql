-- // insert_workflows
-- Migration SQL that makes the change goes here.
DELETE FROM workflows;
INSERT INTO workflows
(id, sn,    name,       description, act_re_procdef_id, service_item_id, service_item_type, process_dictionary_id) VALUES
(1, '001', '工作流1',  '测试',     1,                 1,                 '1',               1),
(2, '002', '工作流2',  '测试',     1,                 1,                 '1',               1),
(3, '003', '工作流3',  '测试',     1,                 1,                 '0',               1),
(4, '004', '工作流4',  '测试',     1,                 1,                 '0',               1),
(5, '005', '工作流5',  '测试',     1,                 1,                 '1',               1),
(6, '006', '工作流6',  '测试',     1,                 1,                 '1',               1);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM workflows;
