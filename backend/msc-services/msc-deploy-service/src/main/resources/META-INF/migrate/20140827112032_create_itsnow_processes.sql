-- // create_itsnow_processes
-- Migration SQL that makes the change goes here.


CREATE TABLE itsnow_processes (
  id            INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  account_id    INT(10) UNSIGNED NOT NULL,
  host_id       INT(10) UNSIGNED NOT NULL,
  schema_id     INT(10) UNSIGNED NOT NULL,
  name          VARCHAR(50)      NOT NULL,
  pid           INT(10) UNSIGNED,
  wd            VARCHAR(255)     NOT NULL,
  configuration VARCHAR(255),
  status        VARCHAR(50)               DEFAULT 'Stopped',
  description   VARCHAR(255),
  created_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (account_id) REFERENCES accounts(id),
  FOREIGN KEY (host_id)    REFERENCES itsnow_hosts(id),
  FOREIGN KEY (schema_id)  REFERENCES itsnow_schemas(id),
  UNIQUE KEY (name)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE itsnow_processes;