INSERT INTO itsnow_hosts (id, name, address, type, capacity, status, configuration, description) VALUES
  (1, 'srv1',     '172.16.3.3', 'COM', 1,  'Running', '{"mem": "16g", "cpu" : "4x2533Mhz", "disk": "100G"}', 'The default host only run MSC, master mysql, redis, nginx'),
  (2, 'App Host', '172.16.3.4', 'APP', 10, 'Running', '{"mem": "8g", "cpu" : "4x2533Mhz", "disk": "100G"}', 'The host runs apps only'),
  (3, 'DB Host', '172.16.3.5', 'DB', 20, 'Running', '{"mem": "8g", "cpu" : "4x2533Mhz", "disk": "100G"}', 'The host runs db only');

