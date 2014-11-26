-- // create_workflows
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS workflows
(
  id                INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sn                VARCHAR(64),
  name              VARCHAR(255),
  description       VARCHAR(500),
  act_re_procdef_id VARCHAR(64),
  service_item_id   INT(10) UNSIGNED,
  service_item_sn   VARCHAR(64),
  service_item_type VARCHAR(1),
  type              VARCHAR(10),
  created_at        TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at        TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS workflows;
