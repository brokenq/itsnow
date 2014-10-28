-- // insert_group_authorities
-- Migration SQL that makes the change goes here.

SET @administrator_gid = (SELECT id from groups where group_name = 'administrators');

INSERT INTO group_authorities(group_id, authority)
VALUES (@administrator_gid, 'ROLE_ADMIN');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM group_authorities;
