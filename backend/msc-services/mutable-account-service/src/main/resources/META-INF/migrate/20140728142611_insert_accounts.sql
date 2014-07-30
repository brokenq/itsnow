-- // insert_accounts
-- Migration SQL that makes the change goes here.

INSERT accounts(sn, name, type, status)
VALUES
 ('msc',     'Itsnow Carrier', 'MscAccount', 'Valid'),
 ('msu_001', 'Itsnow Carrier', 'MsuAccount', 'Valid'),
 ('msu_002', 'Itsnow Carrier', 'MsuAccount', 'New'),
 ('msp_001', 'Itsnow Carrier', 'MspAccount', 'Valid'),
 ('msp_002', 'Itsnow Carrier', 'MspAccount', 'Expired');


-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM accounts;-- WHERE sn IN ('msc','msu_001','msu_002','msp_001','msp_002');
