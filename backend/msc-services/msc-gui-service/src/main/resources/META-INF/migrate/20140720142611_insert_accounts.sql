-- // insert_accounts
-- Migration SQL that makes the change goes here.

INSERT INTO accounts(id, sn, name, domain, type, status)
VALUES (1, 'msc', 'Itsnow Carrier', 'msc', 'msc', 'Valid');


-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM accounts;
