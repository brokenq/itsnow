-- // insert_authorities
-- Migration SQL that makes the change goes here.

INSERT INTO authorities (username, authority) VALUES
  ('msu_user_one', 'ROLE_LINE_ONE'),
  ('msu_user_two', 'ROLE_LINE_TWO');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM authorities;
