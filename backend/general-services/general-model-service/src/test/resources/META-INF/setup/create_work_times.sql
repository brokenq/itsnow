CREATE TABLE IF NOT EXISTS work_times (
   id          INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
   sn          VARCHAR(10)      NOT NULL,
   name        VARCHAR(255)    NOT NULL,
   work_days   VARCHAR(255)     NOT NULL,
   start_at    VARCHAR(10)      NOT NULL,
   end_at      VARCHAR(10)      NOT NULL,
   description VARCHAR(255),
   created_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);

