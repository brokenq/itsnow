-- // create_menu_items
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS menu_items (
  id          INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  parent_id   INT(10) UNSIGNED,
  name       VARCHAR(100)     NOT NULL,
  type       VARCHAR(100)     NOT NULL,
  url         VARCHAR(255)     NOT NULL,
  css         VARCHAR(100),
  show_order  INT(10) UNSIGNED,
  short_cut   VARCHAR(100),
  description VARCHAR(255),
  created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE menu_items;


