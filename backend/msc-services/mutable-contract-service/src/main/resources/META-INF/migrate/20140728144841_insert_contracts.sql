-- // insert contracts
-- Migration SQL that makes the change goes here.

SET @msc_id = (SELECT id from accounts where sn = 'msc');
SET @msu_001_id = (SELECT id from accounts where sn = 'msu_001');
SET @msp_001_id = (SELECT id from accounts where sn = 'msp_001');
SET @msp_002_id = (SELECT id from accounts where sn = 'msp_002');

INSERT INTO contracts(msu_account_id, msp_account_id, sn) VALUES
(@msu_001_id, @msp_002_id, 'SWG-201403080003'),
(@msu_001_id, @msp_001_id, 'SWG-201407010010'),
(@msu_001_id, @msp_001_id, 'SWG-201408050002');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM contracts;
