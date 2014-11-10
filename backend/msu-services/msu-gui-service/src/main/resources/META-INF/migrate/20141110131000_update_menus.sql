-- // insert_authorities
-- Migration SQL that makes the change goes here.

UPDATE menu_items SET state = 'catalog.public.list'
WHERE state = 'services.catalog';

-- //@UNDO
-- SQL to undo the change goes here.


