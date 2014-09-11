-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.
DELETE FROM departments;
INSERT INTO departments (id, sn, name, parent_id, description) VALUES
(1, '001', '财务部', NULL, '测试'),
(2, '002', '人事部', NULL, '测试'),
(3, '003', '销售部', NULL, '测试'),
(4, '004', 'IT部',   NULL, '测试');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM departments;
