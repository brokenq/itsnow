
TRUNCATE TABLE msu_incidents;

INSERT INTO msu_incidents (id,number,requester_name,requester_location,
  requester_email,requester_phone,request_type,request_description,
  service_catalog,category,impact,urgency,
  priority,ci_type,ci,created_at,
  updated_at,created_by,updated_by,assigned_user,
  assigned_group,response_time,resolve_time,close_time,
  solution,close_code,can_process,resolved,
  hardware_error,msu_account_name,msp_account_name,msu_instance_id,
  msp_instance_id,msu_status,msp_status)
VALUES (1,'INC20140915152827084','caojie','shanghai',
  'jacky_cao@dnt.com.cn','13918197099','web','disk error',
  'ma','hardware','high','high',
  'high','host','rp5470-1','2014-09-15 15:28:27',
  '2014-09-15 15:28:27','jacky.cao','jacky.cao',NULL,
  NULL,NULL,NULL,NULL,
  NULL,NULL,NULL,NULL,
  NULL,'DNT',NULL,'1',
  NULL,'New',NULL);
