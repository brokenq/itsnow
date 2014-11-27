CREATE TABLE IF NOT EXISTS sites (
  id           INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  work_time_id INT(10) UNSIGNED,
  sn           VARCHAR(100)     NOT NULL,
  name         VARCHAR(255)     NOT NULL,
  address      VARCHAR(255)     NOT NULL,
  area         VARCHAR(10),
  description  VARCHAR(500),
  created_at   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (work_time_id) REFERENCES work_times (id)
);
