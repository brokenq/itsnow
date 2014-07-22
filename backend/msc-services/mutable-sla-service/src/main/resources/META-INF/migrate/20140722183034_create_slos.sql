-- // create_slos
-- Migration SQL that makes the change goes here.

CREATE TABLE slos (
  id          INT(4) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sla_id      INT(4) UNSIGNED NOT NULL,
  title       VARCHAR(255)    NOT NULL,
  created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (sla_id) REFERENCES slas(id)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE slos;

