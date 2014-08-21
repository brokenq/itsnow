-- // create_icons
-- Migration SQL that makes the change goes here.

CREATE TABLE icons (
  id            INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  base          VARCHAR(100)      NOT NULL,
  sizes         VARCHAR(100)      NOT NULL,
  created_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE icons;


