-- // insert_accounts
-- Migration SQL that makes the change goes here.

INSERT INTO accounts(sn, name, type, status)
VALUES
 ('msc',     'Itsnow Carrier', 'msc', 'Valid'),
 ('msu_001', 'Shanghai VW',    'msu', 'Valid'),
 ('msu_002', 'Shanghai GM',    'msu', 'New'),
 ('msp_001', 'DNT',            'msp', 'Valid'),
 ('msp_002', 'TeamSun',        'msp', 'Expired');


-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM accounts;-- WHERE sn IN ('msc','msu_001','msu_002','msp_001','msp_002');
