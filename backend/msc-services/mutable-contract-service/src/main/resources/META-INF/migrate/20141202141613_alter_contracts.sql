-- // alter_contracts
-- Migration SQL that makes the change goes here.

alter table contracts drop foreign key contracts_ibfk_2;
alter table contracts drop column msp_account_id;
alter table contracts add column title varchar (200);
alter table contracts add column type varchar (64);

-- //@UNDO
-- SQL to undo the change goes here.


