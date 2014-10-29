-- // insert_itsnow_processes
-- Migration SQL that makes the change goes here.


INSERT INTO itsnow_processes(id, account_id, host_id, schema_id, name, status, wd, configuration, description) VALUES
  (1, 1, 1, 1, 'itsnow-msc', 'Running', '/opt/itsnow/msc', '{"http.port": "8400", "jmx.port": "8300", "debug.port" : "8200", "rmi.port" : "8100"}', 'The MSC process'),
  (2, 2, 2, 2, 'itsnow-msu', 'Running', '/opt/itsnow/msu', '{"http.port": "8401", "jmx.port": "8301", "debug.port" : "8201", "rmi.port" : "8101"}', 'The MSU process'),
  (3, 3, 2, 3, 'itsnow-msp', 'Running', '/opt/itsnow/msp', '{"http.port": "8402", "jmx.port": "8302", "debug.port" : "8202", "rmi.port" : "8102"}', 'The MSP process')
;

-- //@UNDO
-- SQL to undo the change goes here.
TRUNCATE TABLE itsnow_processes;

