-- // alter_workflow
-- Migration SQL that makes the change goes here.

alter table workflows drop process_dictionary_id;
alter table workflows add type varchar (10);

-- //@UNDO
-- SQL to undo the change goes here.


