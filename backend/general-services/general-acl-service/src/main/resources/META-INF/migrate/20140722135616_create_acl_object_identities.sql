-- // create_acl_object_identities
-- Migration SQL that makes the change goes here.

CREATE TABLE acl_object_identities (
  id                 INT(4) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  object_id_class    INT(4) UNSIGNED NOT NULL,
  object_id_identity INT(4) UNSIGNED NOT NULL,
  parent_object      INT(4) UNSIGNED,
  owner_sid          INT(4) UNSIGNED,
  entries_inheriting BOOLEAN         NOT NULL,
  created_at         TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at         TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE  KEY uk_acl_object_identity (object_id_class, object_id_identity),
  FOREIGN KEY fk_aoi_parent(parent_object) REFERENCES acl_object_identities(id),
  FOREIGN KEY fk_aoi_class(object_id_class) REFERENCES acl_classes(id),
  FOREIGN KEY fk_aoi_owner(owner_sid) REFERENCES acl_sids(id)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE acl_object_identities;

