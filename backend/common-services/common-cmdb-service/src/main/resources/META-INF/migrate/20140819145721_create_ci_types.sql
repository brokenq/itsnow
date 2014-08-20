-- // create_ci_types
-- Migration SQL that makes the change goes here.

CREATE TABLE ci_types (
  id               INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name             VARCHAR(50)   NOT NULL,
  parent_id        INT(10) UNSIGNED,
  icon_id          INT(10) UNSIGNED,
  description      VARCHAR(255),
  created_at       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (parent_id) REFERENCES ci_types(id),
  FOREIGN KEY (icon_id) REFERENCES icons(id),
  UNIQUE  KEY (name)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE ci_types;

