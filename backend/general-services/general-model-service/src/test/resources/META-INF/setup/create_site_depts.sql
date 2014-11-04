CREATE TABLE IF NOT EXISTS site_depts
(
   site_id INT(10) UNSIGNED,
   dept_id INT(10) UNSIGNED,
   FOREIGN KEY (site_id) REFERENCES sites (id),
   FOREIGN KEY (dept_id) REFERENCES departments (id)
);
