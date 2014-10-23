-- // insert_itsnow_hosts
-- Migration SQL that makes the change goes here.

INSERT INTO itsnow_hosts (id, name, address, type, capacity, status, configuration, description) VALUES
  (1, 'srv1.itsnow.com',     '172.16.3.3', 'DB', 3,  'Running', '{"mem": "8g", "cpu" : "4x2533Mhz", "disk": "100G"}', 'The default host only run MSC, master mysql, redis, nginx'),
  (2, 'MSU/P Host A', '172.16.3.4', 'DB', 20, 'Running', '{"mem": "8g", "cpu" : "4x2533Mhz", "disk": "100G"}', 'The host runs MSU/MSP and mysql slave');

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE  itsnow_hosts;
