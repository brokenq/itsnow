CREATE SCHEMA IF NOT EXISTS itsnow_msc;
CREATE TABLE itsnow_msc.contract_records (
  contract_id INT(10) UNSIGNED NOT NULL,
  msp_account_id  INT(10) UNSIGNED NOT NULL,
  status VARCHAR (64),
  FOREIGN KEY (contract_id) REFERENCES itsnow_msc.contracts (id),
  FOREIGN KEY (msp_account_id) REFERENCES itsnow_msc.accounts (id)
);
