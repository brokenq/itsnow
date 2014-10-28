-- // insert_group_members
-- Migration SQL that makes the change goes here.

# SET @admins_name = 'administrators';
SET @guests_name = 'guests';
# SET @monitors_name = 'monitors';
# SET @reporters_name = 'reporters';
SET @first_line_name = 'first_line';
SET @second_line_name = 'second_line';

# SET @administrator_gid = (SELECT id from groups where group_name = @admins_name);
SET @guest_gid = (SELECT id from groups where group_name = @guests_name);
# SET @monitor_gid = (SELECT id from groups where group_name = @monitors_name);
# SET @reporter_gid = (SELECT id from groups where group_name = @reporters_name);
SET @first_line_gid = (SELECT id from groups where group_name = @first_line_name);
SET @second_line_gid = (SELECT id from groups where group_name = @second_line_name);

INSERT INTO group_members(username, group_id, group_name) VALUES
-- ('admin', @administrator_gid, @admins_name),
-- ('admin', @monitor_gid, @monitors_name),
-- ('admin', @reporter_gid, @reporters_name),
-- ('root',  @administrator_gid, @admins_name),
-- ('root',  @monitor_gid, @monitors_name),
-- ('root',  @reporter_gid, @reporters_name),
# ('steve.li',   @reporter_gid, @reporters_name),
# ('jason.wang', @monitor_gid, @monitors_name),
-- ('stone.xin',  @reporter_gid, @reporters_name),
-- ('jacky.cao',  @monitor_gid, @monitors_name),
-- ('smile.tian', @monitor_gid, @monitors_name),
-- ('sharp.liu',  @reporter_gid, @reporters_name),
-- ('mike.wei',   @monitor_gid, @monitors_name),
-- ('jacky.cao',  @first_line_gid, @first_line),
-- ('jay.xiong',  @second_line_gid, @second_line),
('steve.li',   @first_line_gid, @first_line_name),
('jason.wang', @guest_gid,      @guests_name)
;

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM group_members;
