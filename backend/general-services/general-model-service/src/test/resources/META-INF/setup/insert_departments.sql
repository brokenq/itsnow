-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO departments (sn, name, parent_id, description) VALUES
('001', '财务部', NULL, '测试'),
('002', '人事部', NULL, '测试'),
('003', '销售部', NULL, '测试'),
('004', 'IT部',   NULL, '测试');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM departments;
