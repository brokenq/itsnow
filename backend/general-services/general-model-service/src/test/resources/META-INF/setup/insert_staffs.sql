-- // insert_workflows
-- Migration SQL that makes the change goes here.

SET @USER1 = (SELECT id from itsnow_msc.users where username = 'admin');
SET @USER2 = (SELECT id from itsnow_msc.users where username = 'root');

SET @SITE1 = (SELECT id from sites where sn = '001');
SET @SITE2 = (SELECT id from sites where sn = '002');

SET @DEPT1 = (SELECT id from departments where sn = '001');
SET @DEPT2 = (SELECT id from departments where sn = '002');

INSERT INTO staffs
(no,    name,    mobile_phone,      fixed_phone, email,               title,     type,        status, user_id, site_id, dept_id) VALUES
('001', '赵一',  '15901968800',  '63578888', 'stone@126.com',  '工程师',  'employee', '1',   @USER1,  @SITE2,  @DEPT1),
('002', '钱二',  '15901968800',  '63578888', 'stone@126.com',  '工程师',  'employee', '1',   @USER1,  @SITE2,  @DEPT1),
('003', '孙三',  '15901968800',  '63578888', 'stone@126.com',  '工程师',  'employee', '0',   @USER2,  @SITE1,  @DEPT2),
('004', '李四',  '15901968800',  '63578888', 'stone@126.com',  '工程师',  'employee', '0',   @USER2,  @SITE1,  @DEPT2),
('005', '周五',  '15901968800',  '63578888', 'stone@126.com',  '工程师',  'employee', '1',   @USER2,  @SITE1,  @DEPT1),
('006', '吴六',  '15901968800',  '63578888', 'stone@126.com',  '工程师',  'employee', '1',   @USER2,  @SITE1,  @DEPT1),
('007', '郑七',  '15901968800',  '63578888', 'stone@126.com',  '工程师',  'employee', '1',   @USER2,  @SITE1,  @DEPT1);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM staffs;
