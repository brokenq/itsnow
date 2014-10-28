-- Migration SQL that makes the change goes here.

INSERT INTO roles (name, description) VALUES
( 'ROLE_ADMIN', 'Role with administrative authorities'),
( 'ROLE_ANONYMOUS', 'Role not authenticated');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM roles;
