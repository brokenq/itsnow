-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO site_depts (id, site_id, dept_id) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 2, 1),
(5, 4, 1),
(6, 4, 2);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM site_depts;
