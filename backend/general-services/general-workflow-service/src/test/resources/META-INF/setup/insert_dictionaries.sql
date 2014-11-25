-- // insert_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO dictionaries(code, name, label, detail) VALUES
  ('001', 'area', '区域', '[{"key":"11","value":"北京"},{"key":"12","value":"天津"},{"key":"31","value":"上海"},{"key":"50","value":"重庆"}]');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM dictionaries;
