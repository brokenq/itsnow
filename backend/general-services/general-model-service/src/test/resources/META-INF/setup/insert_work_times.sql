-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO work_times (sn, name, work_days, start_at, end_at, description) VALUES
('plan1', '工作日计划一', '1,2,3,4,5',   '9:00',   '17:30', 'it is test.'),
('plan2', '工作日计划二', '2,3,4,5,6',   '9:30',   '17:30', 'it is test.'),
('plan3', '工作日计划三', '3,4,5,6,0',   '9:30',   '17:30', 'it is test.'),
('plan4', '工作日计划四', '4,5,6,0,1',   '9:30',   '17:30', 'it is test.'),
('plan5', '工作日计划五', '5,6,0,1,2',   '9:30',   '17:30', 'it is test.'),
('plan6', '工作日计划六', '6,0,1,2,3',   '9:30',   '17:30', 'it is test.');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM work_times;
