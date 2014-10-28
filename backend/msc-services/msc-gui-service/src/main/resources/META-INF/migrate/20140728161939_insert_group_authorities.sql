-- // insert_group_authorities
-- Migration SQL that makes the change goes here.

SET @administrator_gid = (SELECT id from groups where group_name = 'administrators');
SET @guest_gid = (SELECT id from groups where group_name = 'guests');
SET @monitor_gid = (SELECT id from groups where group_name = 'monitors');
SET @reporter_gid = (SELECT id from groups where group_name = 'reporters');

INSERT INTO group_authorities (group_id, authority) VALUES
  (@administrator_gid, 'ROLE_ADMIN'),
  (@administrator_gid, 'ROLE_USER'),
  (@monitor_gid, 'ROLE_MONITOR'),
  (@monitor_gid, 'ROLE_USER'),
  (@reporter_gid, 'ROLE_REPORTER'),
  (@reporter_gid, 'ROLE_USER'),
  (@guest_gid, 'ROLE_GUEST');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM group_authorities;
