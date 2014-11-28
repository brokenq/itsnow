-- // insert_group_members
-- Migration SQL that makes the change goes here.

SET @username = 'admin';
SET @group_name = 'administrators';
SET @group_id = (select id from groups where group_name = @group_name);

INSERT INTO group_members(username, group_id, group_name)
VALUES (@username, @group_id, @group_name);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM group_members;
