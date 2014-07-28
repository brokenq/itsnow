-- // insert slas
-- Migration SQL that makes the change goes here.

INSERT INTO slas(title, description)
VALUES
('SLA-001','THE FIRST  DEMO SLA'),
('SLA-002','THE SECOND DEMO SLA'),
('SLA-003','THE THIRD  DEMO SLA');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM slas;
