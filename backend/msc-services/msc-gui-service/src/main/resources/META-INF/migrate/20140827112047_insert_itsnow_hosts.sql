-- // insert_itsnow_hosts
-- Migration SQL that makes the change goes here.

INSERT INTO itsnow_hosts (id, name, address, type, capacity, status, configuration, description) VALUES
  (1, 'srv1', '172.16.3.3', 'COM', 1,  'Running', NULL, 'The default host only run MSC, master mysql, redis, nginx'),
  (2, 'srv2', '172.16.3.4', 'COM', 4,  'Running', NULL, 'The resource host runs demo MSU and MSP, with slave mysql, redis')
;

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE  itsnow_hosts;
