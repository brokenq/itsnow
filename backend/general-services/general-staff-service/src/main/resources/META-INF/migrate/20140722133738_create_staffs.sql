-- // create_staffs
-- Migration SQL that makes the change goes here.

CREATE TABLE staffs (
  id            INT(4) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  department_id INT(4) UNSIGNED NOT NULL,
  user_id       INT(4) UNSIGNED,
  no            VARCHAR(50)     NOT NULL,
  type          VARCHAR(50)     NOT NULL DEFAULT 'employee',
  nick_name     VARCHAR(100),
  email         VARCHAR(100),
  title         VARCHAR(100)    NOT NULL,
  status        VARCHAR(50),
  created_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES itsnow_msc.users(id)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE staffs;
