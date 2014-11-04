CREATE TABLE IF NOT EXISTS staffs (
  id           INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  no           VARCHAR(50)      NOT NULL,
  name         VARCHAR(100),
  mobile_phone VARCHAR(50),
  fixed_phone  VARCHAR(50),
  email        VARCHAR(100),
  title        VARCHAR(100)     NOT NULL,
  type         VARCHAR(50) DEFAULT 'employee',
  status       VARCHAR(50),
  description  VARCHAR(500),
  user_id      INT(10) UNSIGNED,
  site_id      INT(10) UNSIGNED,
  dept_id      INT(10) UNSIGNED,
  created_at   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES itsnow_msc.users (id)
);
