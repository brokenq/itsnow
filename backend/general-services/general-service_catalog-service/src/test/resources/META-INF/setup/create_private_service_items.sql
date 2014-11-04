CREATE TABLE private_service_items(
  id          INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  catalog_id  INT(10) UNSIGNED NOT NULL,
  public_id   INT(10) UNSIGNED,
  sn          VARCHAR(100) NOT NULL,
  title       VARCHAR(255)     NOT NULL,
  brief       VARCHAR(255),
  description VARCHAR(2000),
  icon        VARCHAR(100),
  created_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (catalog_id)     REFERENCES private_service_catalogs(id)
);