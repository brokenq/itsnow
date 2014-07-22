-- // create_acl_sids
-- Migration SQL that makes the change goes here.

CREATE TABLE acl_sids (
  id         INT(4) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  principal  BOOLEAN         NOT NULL,
  sid        VARCHAR(100)    NOT NULL,
  created_at TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_acl_sids(sid, principal)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE acl_sids;
