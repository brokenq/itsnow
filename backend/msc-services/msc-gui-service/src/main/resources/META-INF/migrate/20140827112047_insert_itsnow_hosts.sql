-- // insert_itsnow_hosts
-- Migration SQL that makes the change goes here.

INSERT INTO itsnow_hosts (id, name, address, type, capacity, status, configuration, description) VALUES
  (1, 'srv1',     '172.16.3.3', 'COM', 1,  'Running', '{"mem": "8g", "cpu" : "4x2533Mhz", "disk": "100G"}', 'The default host only run MSC, master mysql, redis, nginx');

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE  itsnow_hosts;
