CREATE TABLE demo_users (
  id         INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name       VARCHAR(25)      NOT NULL,
  nick_name  VARCHAR(50),
  password   VARCHAR(255)     NOT NULL,
  created_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- //@UNDO

DROP TABLE demo_users;