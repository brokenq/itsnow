-- // alter_dictionaries
-- Migration SQL that makes the change goes here.

ALTER TABLE dictionaries DROP sn;
ALTER TABLE dictionaries DROP val;
ALTER TABLE dictionaries DROP state;
ALTER TABLE dictionaries DROP type;
ALTER TABLE dictionaries CHANGE display label VARCHAR(255) NOT NULL;
ALTER TABLE dictionaries ADD detail TEXT NOT NULL;

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS dictionaries;
