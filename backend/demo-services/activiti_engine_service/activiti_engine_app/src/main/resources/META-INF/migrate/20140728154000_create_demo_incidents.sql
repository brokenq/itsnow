CREATE TABLE demo_incidents (
  id         INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  topic    VARCHAR(255),
  solution1    VARCHAR(255),
  solution2    VARCHAR(255),
  close_code    VARCHAR(255),
  resolved    VARCHAR(255),
  owner    VARCHAR(255),
  status    VARCHAR(255),
  created_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- //@UNDO