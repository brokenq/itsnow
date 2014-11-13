CREATE TABLE IF NOT EXISTS ACT_RE_PROCDEF (
  ID_                   varchar(64) NOT NULL,
  REV_                  int(11) DEFAULT NULL,
  CATEGORY_            varchar(255) DEFAULT NULL,
  NAME_                 varchar(255) DEFAULT NULL,
  KEY_                   varchar(255) NOT NULL,
  VERSION_              int(11) NOT NULL,
  DEPLOYMENT_ID_       varchar(64) DEFAULT NULL,
  RESOURCE_NAME_       varchar(4000) DEFAULT NULL,
  DGRM_RESOURCE_NAME_ varchar(4000) DEFAULT NULL,
  DESCRIPTION_         varchar(4000) DEFAULT NULL,
  HAS_START_FORM_KEY_ tinyint(4) DEFAULT NULL,
  SUSPENSION_STATE_   int(11) DEFAULT NULL,
  TENANT_ID_           varchar(255) DEFAULT '',
  PRIMARY KEY (ID_),
  UNIQUE KEY ACT_UNIQ_PROCDEF (KEY_,VERSION_,TENANT_ID_)
);
