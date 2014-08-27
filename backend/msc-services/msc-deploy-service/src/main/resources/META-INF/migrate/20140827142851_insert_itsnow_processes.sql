-- // insert_itsnow_processes
-- Migration SQL that makes the change goes here.

SET @MSC_ACCOUNT_ID = (SELECT id FROM accounts WHERE sn = 'msc');

INSERT INTO itsnow_processes(host_id, schema_id, account_id, name, status, wd, configuration, description) VALUES
  (1, 1, @MSC_ACCOUNT_ID, 'itsnow-msc', 'Running', '/opt/releases/itsnow/msc', '{"http.port": 8071, "jmx.port": 1072, "debug.port" : 1071}', 'The MSC process');

-- //@UNDO
-- SQL to undo the change goes here.
TRUNCATE TABLE itsnow_processes;

