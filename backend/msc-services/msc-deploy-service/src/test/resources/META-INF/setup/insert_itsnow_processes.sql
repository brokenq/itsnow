SET @MSC_ACCOUNT_ID = (SELECT id FROM accounts WHERE sn = 'msc');

INSERT INTO itsnow_processes(host_id, schema_id, account_id, name, status, wd, configuration, description) VALUES
  (1, 1, @MSC_ACCOUNT_ID, 'itsnow-msc', 'Running', '/opt/itsnow/msc', '{"http.port": "8400", "jmx.port": "8300", "debug.port" : "8200", "rmi.port" : "8100"}', 'The MSC process');
