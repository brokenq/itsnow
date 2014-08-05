CREATE TABLE `demo_incidents` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `instance_id` varchar(32) COMMENT '流程实例ID',
  `number` varchar(32) NOT NULL COMMENT '故障单号，系统自动生成，生成规则:INC20140801111700001',
  `requester_name` varchar(255) COMMENT '请求人',
  `requester_location` varchar(255) DEFAULT NULL COMMENT '请求人所处的地区',
  `request_email` varchar(255) DEFAULT NULL COMMENT '请求人的email',
  `request_phone` varchar(255) DEFAULT NULL COMMENT '请求人的电话',
  `service_catalog` varchar(255) DEFAULT NULL COMMENT '服务目录',
  `category` varchar(45) DEFAULT NULL COMMENT '分类：软件、硬件、咨询、其他',
  `impact` varchar(255) DEFAULT NULL COMMENT '影响程度：高、中、低',
  `urgency` varchar(255) DEFAULT NULL COMMENT '紧急度：高、中、低',
  `priority` varchar(255) DEFAULT NULL COMMENT '优先级：高、中、低',
  `request_type` varchar(255) DEFAULT NULL COMMENT '请求类型：email,phone,web',
  `ci_type` varchar(255) DEFAULT NULL COMMENT 'CI类型',
  `ci` varchar(255) DEFAULT NULL COMMENT 'CI',
  `request_description` varchar(255) NOT NULL COMMENT '故障描述',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `assigned_user` varchar(255) DEFAULT NULL COMMENT '分配用户',
  `assigned_group` varchar(255) DEFAULT NULL COMMENT '分配组',
  `response_time` timestamp NULL DEFAULT NULL COMMENT '响应时间',
  `resolve_time` timestamp NULL DEFAULT NULL COMMENT '解决时间',
  `close_time` timestamp NULL DEFAULT NULL COMMENT '关闭时间',
  `solution` varchar(2000) DEFAULT NULL COMMENT '解决方案',
  `status` varchar(255) DEFAULT 'New' COMMENT '当前状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `number_UNIQUE` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- //@UNDO

DROP TABLE demo_incidents;