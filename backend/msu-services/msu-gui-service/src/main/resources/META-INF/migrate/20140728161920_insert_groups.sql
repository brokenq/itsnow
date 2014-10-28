-- // insert_groups
-- Migration SQL that makes the change goes here.
DELETE FROM groups;
INSERT INTO groups(sn, group_name) VALUES
-- ('001', 'administrators'),
('002', 'guests'),
-- ('003', 'monitors'),
-- ('004', 'reporters'),
('005', 'first_line'),
('006', 'second_line');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM groups;
