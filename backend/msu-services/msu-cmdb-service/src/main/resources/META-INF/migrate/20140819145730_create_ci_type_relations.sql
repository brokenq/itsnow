-- // create_ci_type_relations
-- Migration SQL that makes the change goes here.

CREATE TABLE ci_type_relations (
  ci_type_id           INT(10) UNSIGNED NOT NULL,
  ci_relation_type_id  INT(10) UNSIGNED NOT NULL,
  reverse              BOOL             NOT NULL DEFAULT FALSE,
  FOREIGN KEY (ci_type_id) REFERENCES ci_types(id),
  FOREIGN KEY (ci_relation_type_id) REFERENCES ci_relation_types(id)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE ci_type_relations;

