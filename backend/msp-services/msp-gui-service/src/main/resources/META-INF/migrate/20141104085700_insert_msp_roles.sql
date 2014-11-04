-- Migration SQL that makes the change goes here.

INSERT INTO roles (name, description) VALUES
( 'ROLE_LINE_TWO', 'Role line two'),
( 'ROLE_LINE_ONE', 'Role line one'),
( 'ROLE_SERVICE_DESK', 'Role service desk');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM roles;
