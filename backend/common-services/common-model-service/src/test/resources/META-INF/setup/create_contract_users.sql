CREATE TABLE IF NOT EXISTS itsnow_msc.contract_users (
  msp_user_id INT(10) UNSIGNED NOT NULL,
  msu_account_id  INT(10) UNSIGNED NOT NULL,
  FOREIGN KEY (msp_user_id) REFERENCES itsnow_msc.users (id),
  FOREIGN KEY (msu_account_id) REFERENCES itsnow_msc.accounts (id)
);



