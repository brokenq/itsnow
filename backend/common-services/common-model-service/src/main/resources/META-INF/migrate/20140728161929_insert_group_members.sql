-- // insert_group_members
-- Migration SQL that makes the change goes here.

SET @admins_name = 'administrators';
SET @guests_name = 'guests';
SET @monitors_name = 'monitors';
SET @reporters_name = 'reporters';
SET @first_line = 'first_line';
SET @second_line = 'second_line';

SET @administrator_gid = (SELECT id from groups where name = @admins_name);
SET @guest_gid = (SELECT id from groups where name = @guests_name);
SET @monitor_gid = (SELECT id from groups where name = @monitors_name);
SET @reporter_gid = (SELECT id from groups where name = @reporters_name);
SET @first_line_gid = (SELECT id from groups where name = @first_line);
SET @second_line_gid = (SELECT id from groups where name = @second_line);

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
('mike.wei',   @monitor_gid),
('jacky.cao',  @first_line_gid),
('jay.xiong',  @second_line_gid),
('steve.li',  @first_line_gid),
('jason.wang',  @second_line_gid)
;

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM group_members;
