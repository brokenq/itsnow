-- // insert_groups
-- Migration SQL that makes the change goes here.

INSERT INTO groups(name) VALUES
('administrators'),
('guests'),
('monitors'),
('reporters'),
('first_line'),
('second_line');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM groups;
