-- // alter_staffs
-- Migration SQL that makes the change goes here.

alter table staffs modify column status VARCHAR(50) DEFAULT 'Normal';
update staffs set status='Normal' where status='1';
update staffs set status='Quit' where status='0';

-- //@UNDO
-- SQL to undo the change goes here.


