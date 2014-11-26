CREATE TABLE contracts (
  id             INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  msu_account_id INT(10) UNSIGNED NOT NULL,
  msp_account_id INT(10) UNSIGNED,
  sn             VARCHAR(20)      NOT NULL,
  status         VARCHAR(20)      NOT NULL DEFAULT 'Draft',
  created_at     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (msu_account_id) REFERENCES accounts (id),
  FOREIGN KEY (msp_account_id) REFERENCES accounts (id)
);

