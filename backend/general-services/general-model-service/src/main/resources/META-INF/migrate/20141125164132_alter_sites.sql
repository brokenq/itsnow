-- // alter_sites
-- Migration SQL that makes the change goes here.

alter table sites drop FOREIGN KEY sites_ibfk_2;
alter table sites drop process_dictionary_id;
alter table sites add area varchar (10);

-- //@UNDO
-- SQL to undo the change goes here.


