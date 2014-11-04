-- // insert public_service_items
-- Migration SQL that makes the change goes here.

INSERT INTO account_service_items(account_id, item_id)
VALUES
(2,1),
(2,2),
(2,3),
(3,1),
(3,2),
(3,3),
(3,4);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM account_service_items;
