DROP TABLE IF EXISTS itsnow_hosts;
CREATE TABLE itsnow_hosts (
  id            INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name          VARCHAR(50) NOT NULL ,
  address       VARCHAR(50) NOT NULL,
  type          VARCHAR(50) NOT NULL,
  capacity      INT(10) UNSIGNED NOT NULL DEFAULT 20,
  status        VARCHAR(50)               DEFAULT 'Planing',
  configuration VARCHAR(255),
  description   VARCHAR(255),
  created_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY (address)
);

DROP TABLE IF EXISTS itsnow_schemas;
CREATE TABLE itsnow_schemas (
  id            INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  host_id       INT(10) UNSIGNED NOT NULL,
  name          VARCHAR(50)      NOT NULL,
  configuration TEXT,
  description   VARCHAR(255),
  created_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (host_id)        REFERENCES itsnow_hosts(id),
  UNIQUE KEY (name)
);
DROP TABLE IF EXISTS itsnow_processes;

CREATE TABLE itsnow_processes (
  id            INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  account_id    INT(10) UNSIGNED NOT NULL,
  host_id       INT(10) UNSIGNED NOT NULL,
  schema_id     INT(10) UNSIGNED NOT NULL,
  name          VARCHAR(50)      NOT NULL,
  pid           INT(10) UNSIGNED,
  wd            VARCHAR(255)     NOT NULL,
  configuration VARCHAR(255),
  status        VARCHAR(50)               DEFAULT 'Stopped',
  description   VARCHAR(255),
  created_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (account_id) REFERENCES accounts(id),
  FOREIGN KEY (host_id)    REFERENCES itsnow_hosts(id),
  FOREIGN KEY (schema_id)  REFERENCES itsnow_schemas(id),
  UNIQUE KEY (name)
);



INSERT INTO itsnow_hosts (id, name, address, type, capacity, status, configuration, description) VALUES
  (1, 'srv1.itsnow.com',     '172.16.3.3', 'DB', 1,  'Running', '{"mem": "8g", "cpu" : "4x2533Mhz", "disk": "100G"}', 'The default host only run MSC, master mysql, redis, nginx'),
  (2, 'MSU/P Host A', '172.16.3.4', 'DB', 20, 'Running', '{"mem": "8g", "cpu" : "4x2533Mhz", "disk": "100G"}', 'The host runs MSU/MSP and mysql slave');
INSERT INTO itsnow_schemas(id, host_id, name, configuration, description)
  VALUES (1, 1, 'itsnow_msc', '{"user": "root", "password": "secret"}', 'The MSC schema');

SET @MSC_ACCOUNT_ID = (SELECT id FROM accounts WHERE sn = 'msc');

INSERT INTO itsnow_processes(host_id, schema_id, account_id, name, status, wd, configuration, description) VALUES
  (1, 1, @MSC_ACCOUNT_ID, 'itsnow-msc', 'Running', '/opt/releases/itsnow/msc', '{"http.port": "8071", "jmx.port": "1072", "debug.port" : "1071", "rmi.port" : "1073"}', 'The MSC process');
