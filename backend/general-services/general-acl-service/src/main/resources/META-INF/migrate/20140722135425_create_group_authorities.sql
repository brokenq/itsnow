-- // create_group_authorities
-- Migration SQL that makes the change goes here.

CREATE TABLE group_authorities (
    group_id  INT(4) UNSIGNED NOT NULL,
    authority VARCHAR(255)    NOT NULL,
    FOREIGN   KEY (group_id) REFERENCES groups(id)
);


-- //@UNDO
-- SQL to undo the change goes here.


DROP TABLE group_authorities;
