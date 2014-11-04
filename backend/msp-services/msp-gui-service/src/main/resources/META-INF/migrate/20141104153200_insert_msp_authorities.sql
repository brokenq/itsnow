-- // insert_authorities
-- Migration SQL that makes the change goes here.

INSERT INTO authorities (username, authority) VALUES
  ('msp_user_one', 'ROLE_LINE_ONE'),
  ('msp_user_two', 'ROLE_LINE_TWO'),
  ('msp_user_desk','ROLE_SERVICE_DESK');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM authorities;
