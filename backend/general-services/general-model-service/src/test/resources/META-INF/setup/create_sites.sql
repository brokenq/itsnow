-- // create_sites
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS sites (
  id                    INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  process_dictionary_id INT(10) UNSIGNED,
  sn                    VARCHAR(100)      NOT NULL,
  name                  VARCHAR(255)     NOT NULL,
  address               VARCHAR(255)     NOT NULL,
  description           VARCHAR(500),
  work_time_id          INT(10) UNSIGNED,
  created_at            TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at            TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (work_time_id) REFERENCES work_times (id),
  FOREIGN KEY (process_dictionary_id) REFERENCES dictionaries (id)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS sites;
