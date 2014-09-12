-- // insert_group_members
-- Migration SQL that makes the change goes here.

SET @admins_name = 'administrators';
SET @guests_name = 'guests';
SET @monitors_name = 'monitors';
SET @reporters_name = 'reporters';
SET @first_line = 'first_line';
SET @second_line = 'second_line';

SET @steven_li_id = (SELECT id FROM itsnow_msc.users where username = 'steve.li');
SET @sharp_liu_id = (SELECT id FROM itsnow_msc.users where username = 'sharp.liu');
SET @jacky_cao_id = (SELECT id FROM itsnow_msc.users where username = 'jacky.cao');
SET @rose_zhou_id = (SELECT id FROM itsnow_msc.users where username = 'rose.zhou');

SET @administrator_gid = (SELECT id from groups where name = @admins_name);
SET @guest_gid = (SELECT id from groups where name = @guests_name);
SET @monitor_gid = (SELECT id from groups where name = @monitors_name);
SET @reporter_gid = (SELECT id from groups where name = @reporters_name);
SET @first_line_gid = (SELECT id from groups where name = @first_line);
SET @second_line_gid = (SELECT id from groups where name = @second_line);

INSERT INTO group_members(user_id, group_id) VALUES
(@steven_li_id, @administrator_gid),
(@steven_li_id, @monitor_gid),
(@steven_li_id, @reporter_gid),
(@steven_li_id,  @administrator_gid),
(@steven_li_id,  @monitor_gid),
(@steven_li_id,  @reporter_gid),
(@sharp_liu_id,   @reporter_gid),
(@sharp_liu_id, @monitor_gid),
(@sharp_liu_id,  @reporter_gid),
(@jacky_cao_id,  @monitor_gid),
(@jacky_cao_id, @monitor_gid),
(@jacky_cao_id,  @reporter_gid),
(@rose_zhou_id,   @monitor_gid),
(@rose_zhou_id,  @first_line_gid),
(@rose_zhou_id,  @second_line_gid),
(@rose_zhou_id,  @first_line_gid),
(@rose_zhou_id,  @second_line_gid)
;

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM group_members;
