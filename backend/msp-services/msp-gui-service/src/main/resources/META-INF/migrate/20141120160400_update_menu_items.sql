-- // update_menu_items
-- Migration SQL that makes the change goes here.
INSERT INTO menu_items (id, parent_id, name, state, position, css) VALUES
  (45, 4, '我创建的故障单', 'incidents.created', 5, 'icon-eye-close');
-- //@UNDO
-- SQL to undo the change goes here.


