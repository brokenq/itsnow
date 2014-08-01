-- // insert_authorities
-- Migration SQL that makes the change goes here.

INSERT INTO authorities(username, authority) VALUES
('admin',      'ROLE_ADMIN'),
('root',       'ROLE_ADMIN'),
('steve.li',   'ROLE_USER'),
('jason.wang', 'ROLE_ITER'),
('stone.xin',  'ROLE_SERVICE_DESK'),
('jacky.cao',  'ROLE_LINE_ONE'),
('smile.tian', 'ROLE_LINE_TWO'),
('sharp.liu',  'ROLE_USER'),
('mike.wei',   'ROLE_ITER'),
('rose.zhou',  'ROLE_SERVICE_DESK'),
('susie.qian', 'ROLE_LINE_ONE'),
('susie.qian', 'ROLE_LINE_TWO');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM authorities;
