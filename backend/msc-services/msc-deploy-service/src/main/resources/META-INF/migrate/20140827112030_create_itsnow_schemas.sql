-- // create_itsnow_databases
-- Migration SQL that makes the change goes here.

CREATE TABLE itsnow_schemas (
  id            INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  host_id       INT(10) UNSIGNED NOT NULL,
  name          VARCHAR(50)      NOT NULL,
  configuration TEXT,
  description   VARCHAR(255),
  created_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (host_id)        REFERENCES itsnow_hosts(id),
  UNIQUE KEY (name)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE itsnow_schemas;