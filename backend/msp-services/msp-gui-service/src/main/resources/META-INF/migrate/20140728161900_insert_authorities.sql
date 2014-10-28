-- // insert_authorities
-- Migration SQL that makes the change goes here.

INSERT INTO authorities (username, authority) VALUES
-- ('admin',      'ROLE_ADMIN'),
-- ('root',       'ROLE_ADMIN'),
-- ('steve.li',   'ROLE_USER'),
--  ('steve.li',   'ROLE_LINE_ONE'),
-- ('jason.wang', 'ROLE_ITER'),
-- ('jason.wang', 'ROLE_USER'),
  ('stone.xin', 'ROLE_SERVICE_DESK'),
  ('stone.xin', 'ROLE_USER'),
  ('jacky.cao', 'ROLE_LINE_ONE'),
  ('jacky.cao', 'ROLE_USER'),
  ('smile.tian', 'ROLE_LINE_TWO'),
  ('smile.tian', 'ROLE_USER');
-- ('sharp.liu',  'ROLE_USER'),
-- ('mike.wei',   'ROLE_ITER'),
-- ('rose.zhou',  'ROLE_SERVICE_DESK'),
-- ('susie.qian', 'ROLE_LINE_ONE'),
-- ('susie.qian', 'ROLE_LINE_TWO');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM authorities;
