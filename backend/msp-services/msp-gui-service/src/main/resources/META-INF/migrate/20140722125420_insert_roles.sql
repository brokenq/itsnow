-- // insert_authorities
-- Migration SQL that makes the change goes here.
DELETE FROM roles;
INSERT INTO roles (name) VALUES
-- ( 'ROLE_ADMIN'),
  ('ROLE_USER'),
  ('ROLE_ITER'),
  ('ROLE_SERVICE_DESK'),
  ('ROLE_LINE_ONE'),
  ('ROLE_LINE_TWO'),
  ('ROLE_MONITOR'),
  ('ROLE_REPORTER'),
  ('ROLE_GUEST');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM roles;
