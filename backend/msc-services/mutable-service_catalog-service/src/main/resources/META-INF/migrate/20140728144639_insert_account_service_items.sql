-- // insert public_service_items
-- Migration SQL that makes the change goes here.

SET @ITEM100 = (SELECT id FROM public_service_items where title = 'Desktop Clean');
SET @ITEM101 = (SELECT id FROM public_service_items where title = 'Laptop Env configure');
SET @ITEM102 = (SELECT id FROM public_service_items where title = 'Laptop Maintain');

SET @ACC01 = (SELECT id FROM accounts WHERE sn = 'msu_001');
SET @ACC02 = (SELECT id FROM accounts WHERE sn = 'msp_001');

INSERT INTO account_service_items(account_id, item_id)
VALUES
(@ACC02,@ITEM100),
(@ACC02,@ITEM101),
(@ACC02,@ITEM102),
(@ACC01,@ITEM100);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM account_service_items;
