CREATE TABLE IF NOT EXISTS private_service_catalogs (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  parent_id int(10) unsigned NOT NULL,
  parent_type varchar(10) NOT NULL,
  title varchar(255) NOT NULL,
  description tinytext,
  icon varchar(100) DEFAULT NULL,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

