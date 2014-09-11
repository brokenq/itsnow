-- // create_user_roles
-- Migration SQL that makes the change goes here.

CREATE TABLE user_roles
(
   id         INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
   username  VARCHAR(255) NOT NULL,
   authority VARCHAR(255) NOT NULL,
   FOREIGN KEY (authority) REFERENCES roles (name),
   FOREIGN KEY (username) REFERENCES users (username);
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS user_roles;
