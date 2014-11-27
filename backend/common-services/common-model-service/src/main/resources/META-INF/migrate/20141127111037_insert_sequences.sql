-- // insert_sequences
-- Migration SQL that makes the change goes here.

INSERT INTO sequences(catalog, rule) VALUES
('msp', 'MSP_%06d@1010'),
('msu', 'MSU_%06d@1010');

-- //@UNDO
-- SQL to undo the change goes here.


