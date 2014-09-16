-- // insert_itsnow_schemas
-- Migration SQL that makes the change goes here.

INSERT INTO itsnow_schemas(id, host_id, name, configuration, description)
  VALUES (1, 1, 'itsnow_msc', '{"user": "root", "password": "secret"}', 'The MSC schema');

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE itsnow_schemas;
