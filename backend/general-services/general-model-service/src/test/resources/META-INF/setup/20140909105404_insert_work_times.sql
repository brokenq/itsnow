-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO work_times (id, sn, name, work_days, started_at, ended_at, description) VALUES
(1, 'plan1', '工作日计划一', '1,2,3,4,5',   '9:00',   '17:30', 'it is test.'),
(2, 'plan2', '工作日计划二', '2,3,4,5,6',   '9:30',   '17:30', 'it is test.'),
(3, 'plan3', '工作日计划三', '3,4,5,6,0',   '9:30',   '17:30', 'it is test.'),
(4, 'plan4', '工作日计划四', '4,5,6,0,1',   '9:30',   '17:30', 'it is test.'),
(5, 'plan5', '工作日计划五', '5,6,0,1,2',   '9:30',   '17:30', 'it is test.'),
(6, 'plan6', '工作日计划六', '6,0,1,2,3',   '9:30',   '17:30', 'it is test.');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM work_times;
