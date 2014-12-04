CREATE SCHEMA IF NOT EXISTS itsnow_msc;
CREATE TABLE itsnow_msc.contract_users (
  contract_id  INT(10) UNSIGNED NOT NULL,
  msp_user_id INT(10) UNSIGNED NOT NULL,
  access           VARCHAR(1),
  FOREIGN KEY (msp_user_id) REFERENCES itsnow_msc.users (id),
  FOREIGN KEY (contract_id) REFERENCES itsnow_msc.contracts (id)
);



