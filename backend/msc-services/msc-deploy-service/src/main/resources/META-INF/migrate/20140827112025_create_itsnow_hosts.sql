-- // create_itsnow_hosts
-- Migration SQL that makes the change goes here.

CREATE TABLE itsnow_hosts (
  id            INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name          VARCHAR(50) NOT NULL ,
  address       VARCHAR(50) NOT NULL,
  capacity      INT(10) UNSIGNED NOT NULL DEFAULT 20,
  status        VARCHAR(50)               DEFAULT 'Planing',
  configuration VARCHAR(255),
  description   VARCHAR(255),
  created_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY (address)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE itsnow_hosts;