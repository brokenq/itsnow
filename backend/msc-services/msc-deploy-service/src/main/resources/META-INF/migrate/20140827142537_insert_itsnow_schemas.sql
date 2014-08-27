-- // insert_itsnow_schemas
-- Migration SQL that makes the change goes here.

INSERT INTO itsnow_schemas(id, host_id, name, description)
  VALUES (1, 1, 'itsnow_msc', 'The MSC schema');

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE itsnow_schemas;
