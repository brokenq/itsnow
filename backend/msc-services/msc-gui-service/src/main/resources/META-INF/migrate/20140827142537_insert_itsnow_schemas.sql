-- // insert_itsnow_schemas
-- Migration SQL that makes the change goes here.

INSERT INTO itsnow_schemas(id, host_id, name, configuration, description) VALUES
  (1, 1, 'itsnow_msc', '{"user": "itsnow", "password": "secret"}', 'The MSC schema'),
  (2, 2, 'itsnow_msu', '{"user": "itsnow", "password": "secret"}', 'The MSU schema'),
  (3, 2, 'itsnow_msp', '{"user": "itsnow", "password": "secret"}', 'The MSP schema');

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE itsnow_schemas;
