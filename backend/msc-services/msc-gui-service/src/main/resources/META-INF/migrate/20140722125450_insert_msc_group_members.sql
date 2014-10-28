-- // insert_group_members
-- Migration SQL that makes the change goes here.

SET @administrator_name = 'admin';
SET @admins_name = 'administrators';
SET @administrators_gid = (SELECT id from groups where group_name = @admins_name);

INSERT INTO group_members(username, group_id, group_name)
VALUES (@administrator_name, @administrators_gid, @admins_name);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM group_members;
