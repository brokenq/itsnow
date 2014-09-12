-- // insert_group_authorities
-- Migration SQL that makes the change goes here.

SET @administrator_gid = (SELECT id from groups where name = 'administrators');
SET @guest_gid = (SELECT id from groups where name = 'guests');
SET @monitor_gid = (SELECT id from groups where name = 'monitors');
SET @reporter_gid = (SELECT id from groups where name = 'reporters');

SET @ROLE_ADMIN = (SELECT id from roles where name = 'ROLE_ADMIN');
SET @ROLE_USER = (SELECT id from roles where name = 'ROLE_USER');
SET @ROLE_ITER = (SELECT id from roles where name = 'ROLE_ITER');
SET @ROLE_SERVICE_DESK = (SELECT id from roles where name = 'ROLE_SERVICE_DESK');
SET @ROLE_LINE_ONE = (SELECT id from roles where name = 'ROLE_LINE_ONE');
SET @ROLE_LINE_TWO = (SELECT id from roles where name = 'ROLE_LINE_TWO');

INSERT INTO group_authorities(group_id, role_id) VALUES
(@administrator_gid, @ROLE_ADMIN),
(@administrator_gid, @ROLE_USER),
(@monitor_gid,       @ROLE_ITER),
(@monitor_gid,       @ROLE_USER),
(@reporter_gid,      @ROLE_ITER),
(@reporter_gid,      @ROLE_USER),
(@guest_gid,         @ROLE_ITER);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM group_authorities;
