DROP TABLE IF EXISTS msu_incidents;
CREATE TABLE msu_incidents (
  id                     int(10) unsigned NOT NULL AUTO_INCREMENT,
  number                varchar(32) NOT NULL COMMENT '故障单号，系统自动生成，生成规则:INC20140801111700001',
  requester_name       varchar(255) COMMENT '请求人',
  requester_location  varchar(255) DEFAULT NULL COMMENT '请求人所处的地区',
  requester_email      varchar(255) DEFAULT NULL COMMENT '请求人的email',
  requester_phone      varchar(255) DEFAULT NULL COMMENT '请求人的电话',
  request_type         varchar(255) DEFAULT NULL COMMENT '请求类型：email,phone,web',
  request_description varchar(255) NOT NULL COMMENT '故障描述',
  service_catalog     varchar(255) DEFAULT NULL COMMENT '服务目录',
  category             varchar(45) DEFAULT NULL COMMENT '分类：软件、硬件、咨询、其他',
  impact               varchar(255) DEFAULT NULL COMMENT '影响程度：高、中、低',
  urgency              varchar(255) DEFAULT NULL COMMENT '紧急度：高、中、低',
  priority             varchar(255) DEFAULT NULL COMMENT '优先级：高、中、低',
  ci_type              varchar(255) DEFAULT NULL COMMENT 'CI类型',
  ci                    varchar(255) DEFAULT NULL COMMENT 'CI',
  created_at           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  created_by           varchar(255) DEFAULT NULL COMMENT '创建人',
  updated_by           varchar(255) DEFAULT NULL COMMENT '更新人',
  assigned_user       varchar(255) DEFAULT NULL COMMENT '分配用户',
  assigned_group      varchar(255) DEFAULT NULL COMMENT '分配组',
  response_time       timestamp NULL DEFAULT NULL COMMENT '响应时间',
  resolve_time        timestamp NULL DEFAULT NULL COMMENT '解决时间',
  close_time          timestamp NULL DEFAULT NULL COMMENT '关闭时间',
  solution            varchar(2000) DEFAULT NULL COMMENT '解决方案',
  close_code          varchar(255) DEFAULT NULL COMMENT '关闭代码',
  can_process         tinyint(1) DEFAULT NULL COMMENT '能否处理',
  resolved            tinyint(1) DEFAULT NULL COMMENT '是否已解决',
  hardware_error     tinyint(1) DEFAULT NULL COMMENT '是否是硬件故障',
  msu_account_name   varchar(255) DEFAULT NULL COMMENT 'MSU帐户NAME',
  msp_account_name   varchar(255) DEFAULT NULL COMMENT 'MSP帐户NAME',
  msu_instance_id    varchar(255) COMMENT 'MSU流程实例ID',
  msp_instance_id    varchar(255) DEFAULT NULL COMMENT 'MSP流程实例ID',
  msu_status          varchar(255) DEFAULT NULL COMMENT 'MSU当前状态',
  msp_status          varchar(255) DEFAULT NULL COMMENT 'MSP当前状态',
  PRIMARY KEY (id),
  UNIQUE KEY number_UNIQUE (number)
);


-- //@UNDO

DROP TABLE IF EXISTS msu_incidents;