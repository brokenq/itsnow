CREATE TABLE IF NOT EXISTS group_members (
  id           INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username    VARCHAR(50)     NOT NULL,
  group_id    INT(10) UNSIGNED NOT NULL,
  group_name  VARCHAR(50) NOT NULL,
  FOREIGN KEY (group_id) REFERENCES groups (id),
  FOREIGN KEY (username) REFERENCES itsnow_msc.users (username)
);
