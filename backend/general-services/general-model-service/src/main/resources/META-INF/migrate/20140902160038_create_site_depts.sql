-- // create_site_depts
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS site_depts
(
   site_id INT(10) UNSIGNED,
   dept_id INT(10) UNSIGNED,
   FOREIGN KEY (site_id) REFERENCES sites (id),
   FOREIGN KEY (dept_id) REFERENCES departments (id)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS site_depts;
