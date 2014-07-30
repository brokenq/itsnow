-- // insert_authorities
-- Migration SQL that makes the change goes here.

INSERT INTO authorities(username, authority) VALUES
('admin', 'ROLE_ADMIN'),
('root', 'ROLE_ADMIN'),
('monitor', 'ROLE_MONITOR'),
('reporter', 'ROLE_REPORTER'),
('demo', 'ROLE_GUEST'),
('demo', 'ROLE_USER'),
('guest', 'ROLE_GUEST');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM authorities;
