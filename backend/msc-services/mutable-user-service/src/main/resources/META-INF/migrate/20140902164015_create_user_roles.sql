-- // create_user_roles
-- Migration SQL that makes the change goes here.

CREATE TABLE user_roles
(
   id         INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
   user_id   INT(10) UNSIGNED NOT NULL,
   role_id   INT(10) UNSIGNED NOT NULL,
   FOREIGN KEY (role_id) REFERENCES roles (id),
   FOREIGN KEY (user_id) REFERENCES users (id)
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS user_roles;
