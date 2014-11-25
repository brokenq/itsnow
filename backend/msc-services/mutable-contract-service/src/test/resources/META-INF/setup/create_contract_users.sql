CREATE TABLE contract_users (
  msp_user_id INT(10) UNSIGNED NOT NULL,
  msu_account_id  INT(10) UNSIGNED NOT NULL,
  FOREIGN KEY (msp_user_id) REFERENCES users (id),
  FOREIGN KEY (msu_account_id) REFERENCES accounts (id)
);



