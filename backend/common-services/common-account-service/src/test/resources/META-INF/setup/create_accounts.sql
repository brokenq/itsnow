
CREATE TABLE accounts (
  id         INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sn         VARCHAR(20)      NOT NULL,
  name       VARCHAR(255)     NOT NULL,
  type       VARCHAR(100)     NOT NULL,
  status     VARCHAR(50)               DEFAULT 'New',
  created_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY (sn)
);

