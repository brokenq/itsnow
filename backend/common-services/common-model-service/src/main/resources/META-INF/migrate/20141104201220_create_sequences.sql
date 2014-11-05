-- // create_sequences
-- Migration SQL that makes the change goes here.

CREATE TABLE IF NOT EXISTS sequences (
  catalog     VARCHAR(50)      NOT NULL,
  rule        VARCHAR(255)     NOT NULL,
  value       INT(10)          NOT NULL DEFAULT 1,
  increment   INT(10)          NOT NULL DEFAULT 1,
  PRIMARY KEY (catalog)
);

DROP FUNCTION IF EXISTS curr_val;
DELIMITER $
CREATE FUNCTION curr_val (name VARCHAR(50))
         RETURNS INTEGER
         LANGUAGE SQL
         DETERMINISTIC
         CONTAINS SQL
         SQL SECURITY DEFINER
         COMMENT ''
BEGIN
         DECLARE result INTEGER;
         SET result = 0;
         SELECT value INTO result
                   FROM sequences
                   WHERE catalog = name;
         RETURN result;
END
$
DELIMITER ;

DROP FUNCTION IF EXISTS next_val;
DELIMITER $
CREATE FUNCTION next_val (name VARCHAR(50))
         RETURNS INTEGER
         LANGUAGE SQL
         DETERMINISTIC
         CONTAINS SQL
         SQL SECURITY DEFINER
         COMMENT ''
BEGIN
         UPDATE sequences
                   SET value = value + increment
                   WHERE catalog = name;
         RETURN curr_val(name);
END
$
DELIMITER ;

DROP FUNCTION IF EXISTS set_val;
DELIMITER $
CREATE FUNCTION set_val (name VARCHAR(50), val INTEGER)
         RETURNS INTEGER
         LANGUAGE SQL
         DETERMINISTIC
         CONTAINS SQL
         SQL SECURITY DEFINER
         COMMENT ''
BEGIN
         UPDATE sequences
                   SET value = val
                   WHERE catalog = name;
         RETURN curr_val(name);
END
$
DELIMITER ;



-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE sequences;
