-- // alter_departments
-- Migration SQL that makes the change goes here.

alter table departments modify sn varchar(64);

-- //@UNDO
-- SQL to undo the change goes here.


