
CREATE TABLE IF NOT EXISTS accounts (
  id          INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id     INT(10) UNSIGNED,
  sn          VARCHAR(20)      NOT NULL,
  domain      VARCHAR(50)      NOT NULL,
  name        VARCHAR(255)     NOT NULL,
  type        VARCHAR(100)     NOT NULL,
  status      VARCHAR(50)               DEFAULT 'New',
  description VARCHAR(255),
  created_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY (sn),
  UNIQUE KEY (domain)
);

