-- // create_process_dictionary
-- Migration SQL that makes the change goes here.

CREATE TABLE act_re_procdef (
  id_                   varchar(64) NOT NULL,
  rev_                  int(11) DEFAULT NULL,
  category_            varchar(255) DEFAULT NULL,
  name_                 varchar(255) DEFAULT NULL,
  key_                   varchar(255) NOT NULL,
  version_              int(11) NOT NULL,
  deployment_id_       varchar(64) DEFAULT NULL,
  resource_name_       varchar(4000) DEFAULT NULL,
  dgrm_resource_name_ varchar(4000) DEFAULT NULL,
  description_         varchar(4000) DEFAULT NULL,
  has_start_form_key_ tinyint(4) DEFAULT NULL,
  suspension_state_   int(11) DEFAULT NULL,
  tenant_id_           varchar(255) DEFAULT '',
  PRIMARY KEY (id_),
  UNIQUE KEY act_uniq_procdef (key_,version_,tenant_id_)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS act_re_procdef;
