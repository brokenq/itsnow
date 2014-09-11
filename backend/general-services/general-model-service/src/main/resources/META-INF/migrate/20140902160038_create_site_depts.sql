-- // create_site_depts
-- Migration SQL that makes the change goes here.

CREATE TABLE site_depts
(
   id       INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
   site_id INT(10) UNSIGNED,
   dept_id INT(10) UNSIGNED,
   FOREIGN KEY (site_id) REFERENCES sites (id),
   FOREIGN KEY (dept_id) REFERENCES departments (id)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS site_depts;
