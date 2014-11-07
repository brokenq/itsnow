-- // insert_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO dictionaries(sn, code, name, display, val, state, type) VALUES
  ('001', 'inc003', '区域', '北京', '11', '1', '1'),
  ('002', 'inc003', '区域', '天津', '12', '1', '1'),
  ('003', 'inc003', '区域', '上海', '31', '1', '1'),
  ('004', 'inc003', '区域', '重庆', '50', '1', '1');

-- //@UNDO
-- SQL to undo the change goes here.


