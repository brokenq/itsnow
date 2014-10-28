
CREATE TABLE IF NOT EXISTS private_service_items (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  catalog_id int(10) unsigned NOT NULL,
  catalog_type varchar(20) NOT NULL DEFAULT 'public',
  title varchar(255) NOT NULL,
  brief varchar(255) DEFAULT NULL,
  description tinytext,
  icon varchar(100) DEFAULT NULL,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

