CREATE TABLE IF NOT EXISTS users (
  id                INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  account_id       INT(20) UNSIGNED,
  username          VARCHAR(25)     NOT NULL UNIQUE,
  nick_name         VARCHAR(25),
  email             VARCHAR(255),
  phone             VARCHAR(50),
  password          VARCHAR(255)   NOT NULL,
  enabled           BOOLEAN          NOT NULL DEFAULT TRUE,
  expired           BOOLEAN          NOT NULL DEFAULT FALSE,
  locked             BOOLEAN         NOT NULL DEFAULT FALSE,
  password_expired BOOLEAN          NOT NULL DEFAULT FALSE,
  created_at        TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at        TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (account_id) REFERENCES accounts(id)
);
