-- // insert public_service_items
-- Migration SQL that makes the change goes here.

UPDATE public_service_items set sn = 'SC_1021' WHERE title = 'General Hardware Problem';
UPDATE public_service_items set sn = 'SC_10111' WHERE title = 'Desktop Clean';
UPDATE public_service_items set sn = 'SC_1022' WHERE title = 'Laptop Maintain';
UPDATE public_service_items set sn = 'SC_10112' WHERE title = 'Laptop Env configure';

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM public_service_items;
