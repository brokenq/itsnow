-- // insert_accounts
-- Migration SQL that makes the change goes here.

INSERT INTO accounts(sn, name, domain, type, status)
VALUES
 ('msc',     'Itsnow Carrier', 'www',     'msc', 'Valid'),
 ('msu_001', 'Shanghai VW',    'csvw',    'msu', 'Valid'),
 ('msu_002', 'Shanghai GM',    'csgm',    'msu', 'New'),
 ('msp_001', 'DNT',            'dnt',     'msp', 'Valid'),
 ('msp_002', 'TeamSun',        'teamsun', 'msp', 'Expired');


-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM accounts;-- WHERE sn IN ('msc','msu_001','msu_002','msp_001','msp_002');
