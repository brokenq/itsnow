DELETE FROM sequences;
INSERT INTO sequences(catalog, rule, value, increment) VALUES
('TEST', 'TEST_%06d@1000', 1100, 1),
('BASE', 'BASE_%06d@1000/10', 1010, 10),
('STEP', 'STEP_%06d@0100/2',  100, 2);
