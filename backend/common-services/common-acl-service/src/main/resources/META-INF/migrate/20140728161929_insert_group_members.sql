-- // insert_group_members
-- Migration SQL that makes the change goes here.

SET @administrator_gid = (SELECT id from groups where group_name = 'administrators');
SET @guest_gid = (SELECT id from groups where group_name = 'guests');
SET @monitor_gid = (SELECT id from groups where group_name = 'monitors');
SET @reporter_gid = (SELECT id from groups where group_name = 'reporters');

INSERT INTO group_members(username, group_id) VALUES
('admin', @administrator_gid),
('admin', @monitor_gid),
('admin', @reporter_gid),
('root',  @administrator_gid),
('root',  @monitor_gid),
('root',  @reporter_gid),
('steve.li',   @reporter_gid),
('jason.wang', @monitor_gid),
('stone.xin',  @reporter_gid),
('jacky.cao',  @monitor_gid),
('smile.tian', @monitor_gid),
('sharp.liu',  @reporter_gid),
('mike.wei',   @monitor_gid);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM group_members;
