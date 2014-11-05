CREATE TABLE IF NOT EXISTS sequences (
  catalog     VARCHAR(50)      NOT NULL,
  rule        VARCHAR(255)     NOT NULL,
  value       INT(10)          NOT NULL DEFAULT 1,
  increment   INT(10)          NOT NULL DEFAULT 1,
  PRIMARY KEY (catalog)
);
