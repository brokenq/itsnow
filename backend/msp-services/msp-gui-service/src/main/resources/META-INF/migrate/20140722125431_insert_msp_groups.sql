-- // insert_groups
-- Migration SQL that makes the change goes here.
DELETE FROM groups;
INSERT INTO groups(sn, group_name) VALUES
('001', 'administrators');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM groups;
