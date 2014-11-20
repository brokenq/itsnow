CREATE TABLE contract_users (
  contract_id INT(10) UNSIGNED NOT NULL,
  msp_user_id INT(10) UNSIGNED NOT NULL,
  account_sn  VARCHAR(64),
  access      VARCHAR(1),
  FOREIGN KEY (contract_id) REFERENCES contracts (id),
  FOREIGN KEY (msp_user_id) REFERENCES users (id)
);



