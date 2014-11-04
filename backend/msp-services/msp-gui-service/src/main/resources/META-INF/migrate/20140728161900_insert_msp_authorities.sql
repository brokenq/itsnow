-- // insert_authorities
-- Migration SQL that makes the change goes here.

INSERT INTO authorities (username, authority) VALUES
  ('msp_admin', 'ROLE_ADMIN');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM authorities;
