-- // insert contract_details
-- Migration SQL that makes the change goes here.

SET @swg0003 = (SELECT id from contracts where sn = 'SWG-201403080003');
SET @swg0010 = (SELECT id from contracts where sn = 'SWG-201407010010');
SET @swg0002 = (SELECT id from contracts where sn = 'SWG-201408050002');

INSERT INTO contract_details(contract_id, title, brief, description, icon) VALUES
(@swg0003, '项目1', '第一个明细', '第一个详细说明', '/assets/cd01.png'),
(@swg0003, '项目2', '第二个明细', '第二个详细说明', '/assets/cd02.png'),
(@swg0003, '项目3', '第三个明细', '第三个详细说明', '/assets/cd03.png'),
(@swg0003, '项目4', '第四个明细', '第四个详细说明', '/assets/cd04.png'),
(@swg0010, '项目A', 'A明细', '第A个详细说明', '/assets/cd.png'),
(@swg0010, '项目B', 'B明细', '第B个详细说明', '/assets/cd.png'),
(@swg0010, '项目C', 'C明细', '第C个详细说明', '/assets/cd.png'),
(@swg0002, '项目X', 'X明细', '第X个详细说明', '/assets/cd.png'),
(@swg0002, '项目Y', 'Y明细', '第Y个详细说明', '/assets/cd.png'),
(@swg0002, '项目Z', 'Z明细', '第Z个详细说明', '/assets/cd.png');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM contract_details;
