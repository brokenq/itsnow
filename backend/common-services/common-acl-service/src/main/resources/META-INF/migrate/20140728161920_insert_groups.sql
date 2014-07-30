-- // insert_groups
-- Migration SQL that makes the change goes here.

INSERT INTO groups(group_name) VALUES
('administrators'),
('guests'),
('monitors'),
('reporters');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM groups;
