-- // insert_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO work_times (sn, name, work_days, start_at, end_at, description) VALUES
('plan1', '周一、二、三、四、五', '1,2,3,4,5',   '9:00',   '17:30', 'it is test.'),
('plan2', '周二、三、四、五、六', '2,3,4,5,6',   '9:30',   '17:30', 'it is test.'),
('plan3', '周三、四、五、六、日', '3,4,5,6,0',   '9:30',   '17:30', 'it is test.'),
('plan4', '周一、四、五、六、日', '4,5,6,0,1',   '9:30',   '17:30', 'it is test.'),
('plan5', '周一、二、五、六、日', '5,6,0,1,2',   '9:30',   '17:30', 'it is test.'),
('plan6', '周一、二、三、六、日', '6,0,1,2,3',   '9:30',   '17:30', 'it is test.');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM work_times;
