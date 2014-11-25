-- // create_contract_users
-- MSP users can login to the MSU through this relationship
-- Migration SQL that makes the change goes here.

CREATE TABLE contract_users (
  msp_user_id INT(10) UNSIGNED NOT NULL,
  msu_account_id  INT(10) UNSIGNED NOT NULL,
  FOREIGN KEY (msp_user_id) REFERENCES users (id),
  FOREIGN KEY (msu_account_id) REFERENCES accounts (id)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE contract_users;
