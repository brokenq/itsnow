-- // insert_group_authorities
-- Migration SQL that makes the change goes here.

SET @administrator_gid = 1;

INSERT INTO group_authorities(group_id, authority)
VALUES (@administrator_gid, 'ROLE_ADMIN');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM group_authorities;
