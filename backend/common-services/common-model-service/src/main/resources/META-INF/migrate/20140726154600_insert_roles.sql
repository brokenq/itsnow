-- // insert_authorities
-- Migration SQL that makes the change goes here.
DELETE FROM roles;
INSERT INTO roles (sn, name) VALUES
( '001', 'ROLE_ADMIN'),
( '002', 'ROLE_USER'),
( '003', 'ROLE_ITER'),
( '004', 'ROLE_SERVICE_DESK'),
( '005', 'ROLE_LINE_ONE'),
( '006', 'ROLE_LINE_TWO');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM roles;
