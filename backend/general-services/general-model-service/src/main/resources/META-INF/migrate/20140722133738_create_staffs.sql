-- // create_staffs
-- Migration SQL that makes the change goes here.

CREATE TABLE staffs (
  id            INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name          VARCHAR(100),
  no             VARCHAR(50)    NOT NULL,
  mobile_phone VARCHAR(50),
  fixed_phone  VARCHAR(50),
  email         VARCHAR(100),
  title         VARCHAR(100)    NOT NULL,
  type          VARCHAR(50)     NOT NULL DEFAULT 'employee',
  status        VARCHAR(50),
  description  VARCHAR(500),
  user_id       INT(10) UNSIGNED,
  site_dept_id INT(10) UNSIGNED,
  created_at   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (site_dept_id) REFERENCES site_depts (id)
--   ,FOREIGN KEY (user_id) REFERENCES users (id)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS staffs;
