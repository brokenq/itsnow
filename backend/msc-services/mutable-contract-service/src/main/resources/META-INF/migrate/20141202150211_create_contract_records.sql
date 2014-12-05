-- // create_contract_records
-- Migration SQL that makes the change goes here.

CREATE TABLE contract_records (
  contract_id INT(10) UNSIGNED NOT NULL,
  msp_account_id  INT(10) UNSIGNED NOT NULL,
  status VARCHAR (64),
  created_at     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (contract_id) REFERENCES contracts (id),
  FOREIGN KEY (msp_account_id) REFERENCES accounts (id)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE contract_records;
