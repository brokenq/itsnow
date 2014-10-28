-- Migration SQL that makes the change goes here.
DELETE FROM roles;
INSERT INTO roles (name) VALUES
( 'ROLE_ADMIN'),
( 'ROLE_ANONYMOUS');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM roles;
