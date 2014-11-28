
SET @SITE1 = (SELECT id from sites where sn = '001');
SET @SITE2 = (SELECT id from sites where sn = '002');

SET @DEPT1 = (SELECT id from departments where sn = '001');
SET @DEPT2 = (SELECT id from departments where sn = '002');

INSERT INTO staffs
(no, name, mobile_phone, fixed_phone, email, title, type, status, user_id, site_id, dept_id) VALUES
  ('001', '赵一', '15901968801', '63578881', 'one@126.com', '工程师', 'employee', 'Normal', NULL, @SITE2, @DEPT1),
  ('002', '钱二', '15901968802', '63578882', 'two@126.com', '设计师', 'employee', 'Normal', NULL, @SITE2, @DEPT1),
  ('003', '孙三', '15901968803', '63578883', 'three@126.com', '运维', 'employee', 'Quit', NULL, @SITE1, @DEPT2),
  ('004', '李四', '15901968804', '63578884', 'four@126.com', '前端工程师', 'employee', 'Quit', NULL, @SITE1, @DEPT2),
  ('005', '周五', '15901968805', '63578885', 'five@126.com', '架构师', 'employee', 'Normal', NULL, @SITE1, @DEPT1),
  ('006', '吴六', '15901968806', '63578886', 'six@126.com', '咨询师', 'employee', 'Normal', NULL, @SITE1, @DEPT1);