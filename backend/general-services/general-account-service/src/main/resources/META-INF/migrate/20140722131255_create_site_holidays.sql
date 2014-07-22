-- // create_site_holidays
-- Migration SQL that makes the change goes here.

CREATE TABLE site_holidays (
  site_id     INT(4) UNSIGNED NOT NULL,
  holiday_id  INT(4) UNSIGNED NOT NULL,
  FOREIGN KEY (site_id) REFERENCES sites(id),
  FOREIGN KEY (holiday_id) REFERENCES holidays(id)
);



-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE site_holidays;
