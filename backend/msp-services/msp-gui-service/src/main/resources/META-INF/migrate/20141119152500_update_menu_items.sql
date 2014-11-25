-- // update_menu_items
-- Migration SQL that makes the change goes here.
INSERT INTO menu_items (id, parent_id, name, state, position, css) VALUES
  (44, 4, '监控的故障单', 'incidents.monitored', 4, 'icon-eye-close');
-- //@UNDO
-- SQL to undo the change goes here.


