CREATE TABLE private_service_catalogs (
  id          INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  parent_id   INT(10) UNSIGNED,
  public_id   INT(10) UNSIGNED,
  sn          VARCHAR(20)      NOT NULL,
  title       VARCHAR(255)     NOT NULL,
  description VARCHAR(2000),
  icon        VARCHAR(100),
  level INT(10) NOT NULL DEFAULT 1,
  created_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (parent_id)      REFERENCES private_service_catalogs(id)
);
