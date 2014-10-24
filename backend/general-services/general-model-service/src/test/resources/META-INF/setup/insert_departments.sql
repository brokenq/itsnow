-- // insert_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO departments (sn, name, parent_id, position, description) VALUES
  (1, '001', '财务部',   NULL, 1, '测试'),
  (2, '002', '人事部',   NULL, 2, '测试'),
  (3, '003', '销售部',   NULL, 3, '测试'),
  (4, '004', 'IT部',     NULL, 4, '测试'),
  (5, '005', '财务一部', 1,     1, '测试'),
  (6, '006', '人事一部', 2,     1, '测试'),
  (7, '007', '销售一部', 3,     1, '测试'),
  (8, '008', 'IT一部',   4,     1, '测试'),
  (9, '009', 'IT二部',   4,     2, '测试');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM departments;
