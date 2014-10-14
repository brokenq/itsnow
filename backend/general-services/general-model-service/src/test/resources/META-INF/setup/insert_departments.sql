-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO departments (sn, name, parent_id, position, description) VALUES
('001', '财务部', NULL, 1, '测试'),
('002', '人事部', NULL, 2, '测试'),
('003', '销售部', NULL, 3, '测试'),
('004', 'IT部',   NULL, 4, '测试');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM departments;
