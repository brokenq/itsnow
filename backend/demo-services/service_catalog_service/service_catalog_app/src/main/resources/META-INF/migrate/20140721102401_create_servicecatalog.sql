CREATE TABLE demo_service_catalogs (
  id         INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sc_name    VARCHAR(25)      NOT NULL,
  sc_desc    VARCHAR(50),
  created_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- //@UNDO

DROP TABLE demo_service_catalogs;
