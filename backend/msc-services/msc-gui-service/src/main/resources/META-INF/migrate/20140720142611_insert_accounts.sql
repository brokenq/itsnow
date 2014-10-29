-- // insert_accounts
-- Migration SQL that makes the change goes here.

INSERT INTO accounts(id, sn, name, domain, type, status)
VALUES
  (1, 'msc', 'Itsnow Carrier',          'msc', 'msc', 'Valid'),
  (2, 'msu', 'Itsnow Enterprise User',  'msu', 'msu', 'Valid'),
  (3, 'msp', 'Itsnow Service Provider', 'msp', 'msp', 'Valid')
;


-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM accounts;
