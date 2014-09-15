-- // create_workflows
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS workflows
(
   id                         INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
   sn                         VARCHAR(10),
   name                       VARCHAR(255),
   description               VARCHAR(500),
   act_re_procdef_id        INT(10) UNSIGNED,
   service_item_id         INT(10) UNSIGNED,
   service_item_type       VARCHAR(1),
   process_dictionary_id   INT(10) UNSIGNED,
   created_at                TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at                TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
--    FOREIGN KEY (act_re_procdef_id) REFERENCES act_re_procdef (id_),
--    FOREIGN KEY (process_dictionary_id) REFERENCES process_dictionaries (id)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS workflows;
