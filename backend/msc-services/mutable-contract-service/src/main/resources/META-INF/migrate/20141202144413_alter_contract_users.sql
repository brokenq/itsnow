-- // alter_contract_users
-- Migration SQL that makes the change goes here.

ALTER TABLE contract_users
  DROP FOREIGN KEY contract_users_ibfk_2;

ALTER TABLE contract_users
  DROP COLUMN msu_account_id;

ALTER TABLE contract_users
  ADD COLUMN contract_id INT(10) UNSIGNED NOT NULL;

ALTER TABLE contract_users
  ADD CONSTRAINT fk_contract_users_id FOREIGN KEY (contract_id) REFERENCES contracts (
  id);

alter table contract_users add column access varchar (1);

-- //@UNDO
-- SQL to undo the change goes here.


