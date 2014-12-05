-- // create_contract_details
-- Migration SQL that makes the change goes here.
CREATE SCHEMA IF NOT EXISTS itsnow_msc;
CREATE TABLE itsnow_msc.contract_details (
  id          INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  contract_id INT(10) UNSIGNED NOT NULL,
  title       VARCHAR(255)     NOT NULL,
  brief       VARCHAR(255),
  description TEXT(16),
  icon        VARCHAR(100),
  item_id     INT(10) UNSIGNED,
  created_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (contract_id) REFERENCES contracts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE contract_details;
