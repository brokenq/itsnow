-- // insert_accounts
-- Migration SQL that makes the change goes here.

INSERT accounts(sn, name, type, status)
VALUES
 ('msc',     'Itsnow Carrier', 'msc', 'Valid'),
 ('msu_001', 'Itsnow Carrier', 'msu', 'Valid'),
 ('msu_002', 'Itsnow Carrier', 'msu', 'New'),
 ('msp_001', 'Itsnow Carrier', 'msp', 'Valid'),
 ('msp_002', 'Itsnow Carrier', 'msp', 'Expired');


-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM accounts;-- WHERE sn IN ('msc','msu_001','msu_002','msp_001','msp_002');
