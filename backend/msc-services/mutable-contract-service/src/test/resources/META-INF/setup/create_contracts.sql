CREATE SCHEMA IF NOT EXISTS itsnow_msc;
CREATE TABLE itsnow_msc.contracts (
  id             INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  msu_account_id INT(10) UNSIGNED NOT NULL,
  sn             VARCHAR(20)      NOT NULL,
  title          VARCHAR(200),
  type           VARCHAR(64),
  status         VARCHAR(20)      NOT NULL DEFAULT 'Draft',
  created_at     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (msu_account_id) REFERENCES itsnow_msc.accounts (id)
);

