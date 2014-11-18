-- // alter_sites
-- Migration SQL that makes the change goes here.

alter table sites modify sn varchar(64);

-- //@UNDO
-- SQL to undo the change goes here.


