-- // create_acl_entries
-- Migration SQL that makes the change goes here.

CREATE TABLE acl_entries (
  id                  INT(4) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  acl_object_identity INT(4) UNSIGNED NOT NULL,
  ace_order           INT(4) NOT NULL,
  sid                 INT(4) UNSIGNED NOT NULL,
  mask                INT(4) UNSIGNED NOT NULL,
  granting            BOOLEAN NOT NULL,
  audit_success       BOOLEAN NOT NULL,
  audit_failure       BOOLEAN NOT NULL,
  UNIQUE KEY unique_acl_entry (acl_object_identity, ace_order),
  FOREIGN KEY fk_acl_entry_object(acl_object_identity) REFERENCES acl_object_identities(id),
  FOREIGN KEY fk_acl_entry_acl(sid) REFERENCES acl_sids(id)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE acl_entries;
