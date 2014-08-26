-- // create_sites
-- Migration SQL that makes the change goes here.

CREATE TABLE sites (
  id         INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sn         VARCHAR(20)      NOT NULL,
  name       VARCHAR(255)     NOT NULL,
  address    VARCHAR(255)     NOT NULL,
  work_time  VARCHAR(500)     NOT NULL,
  created_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE sites;

