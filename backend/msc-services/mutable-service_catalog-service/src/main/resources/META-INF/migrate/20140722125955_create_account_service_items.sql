-- // create_account_service_items
-- Migration SQL that makes the change goes here.

CREATE TABLE account_service_items (
  id          INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  account_id  INT(10) UNSIGNED NOT NULL,
  item_id     INT(10) UNSIGNED NOT NULL,
  created_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (account_id)     REFERENCES accounts(id),
  FOREIGN KEY (item_id)        REFERENCES public_service_items(id)
);


-- //@UNDO
-- SQL to undo the change goes here.


DROP TABLE account_service_items;