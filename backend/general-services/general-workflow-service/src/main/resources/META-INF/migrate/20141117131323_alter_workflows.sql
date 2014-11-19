-- // modify_workflows
-- Migration SQL that makes the change goes here.

alter table workflows modify sn varchar(64);
alter table workflows modify act_re_procdef_id varchar(64);
alter table workflows add service_item_sn varchar(64);

-- //@UNDO
-- SQL to undo the change goes here.


