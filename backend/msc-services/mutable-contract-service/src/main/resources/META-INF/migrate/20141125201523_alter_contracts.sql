-- // alter_contracts
-- Migration SQL that makes the change goes here.

alter table contracts drop column msu_status;
alter table contracts drop column msp_status;
alter table contracts add column status varchar (64);

-- //@UNDO
-- SQL to undo the change goes here.


