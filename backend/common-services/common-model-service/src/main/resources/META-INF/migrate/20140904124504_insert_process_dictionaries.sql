-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO process_dictionaries(id, code, name, level, level_name, state, type) VALUES
(1, 'inc001', '优先级',   'high',   '高', '1', '1'),
(2, 'inc002', '优先级',   'middle', '中', '1', '1'),
(3, 'inc003', '优先级',   'low',    '低', '1', '1'),
(4, 'inc004', '影响程度', 'high',   '高', '1', '1'),
(5, 'inc005', '影响程度', 'middle', '中', '1', '1'),
(6, 'inc006', '影响程度', 'low',    '低', '1', '1');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM process_dictionaries;
