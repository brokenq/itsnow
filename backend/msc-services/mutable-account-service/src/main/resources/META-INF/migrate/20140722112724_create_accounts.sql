-- // create_accounts
-- Migration SQL that makes the change goes here.

CREATE TABLE accounts (
  id         INT(4) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sn         VARCHAR(20)     NOT NULL,
  name       VARCHAR(255)    NOT NULL,
  type       VARCHAR(100)    NOT NULL,
  status     VARCHAR(50),
  created_at TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE accounts;