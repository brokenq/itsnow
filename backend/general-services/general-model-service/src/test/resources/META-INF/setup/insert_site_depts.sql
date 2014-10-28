-- // insert_dictionaries
-- Migration SQL that makes the change goes here.

SET @SITEID1 = (SELECT id from sites where sn = '001');
SET @SITEID2 = (SELECT id from sites where sn = '002');
SET @SITEID3 = (SELECT id from sites where sn = '003');

SET @DEPTID1 = (SELECT id from departments where sn = '001');
SET @DEPTID2 = (SELECT id from departments where sn = '002');
SET @DEPTID3 = (SELECT id from departments where sn = '003');

INSERT INTO site_depts (site_id, dept_id) VALUES
(@SITEID1, @DEPTID1),
(@SITEID1, @DEPTID2),
(@SITEID1, @DEPTID3),
(@SITEID2, @DEPTID1),
(@SITEID2, @DEPTID1),
(@SITEID3, @DEPTID2);

