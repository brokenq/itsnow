-- // create_account_service_items
-- Migration SQL that makes the change goes here.

CREATE TABLE itsnow_msc.account_service_catalogs (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  account_id INT(10) UNSIGNED NULL,
  catalog_id INT(10) UNSIGNED NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX fk_account_service_catalogs_1_idx (account_id ASC),
  INDEX fk_account_service_catalogs_2_idx (catalog_id ASC),
  CONSTRAINT fk_account_service_catalogs_1
    FOREIGN KEY (account_id)
    REFERENCES itsnow_msc.accounts (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_account_service_catalogs_2
    FOREIGN KEY (catalog_id)
    REFERENCES itsnow_msc.public_service_catalogs (id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- //@UNDO
-- SQL to undo the change goes here.


DROP TABLE account_service_catalogs;