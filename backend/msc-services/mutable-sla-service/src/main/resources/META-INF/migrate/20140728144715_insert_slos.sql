-- // insert slos
-- Migration SQL that makes the change goes here.

SET @SLA001 = (SELECT ID FROM slas where title = 'SLA-001');
SET @SLA002 = (SELECT ID FROM slas where title = 'SLA-002');
SET @SLA003 = (SELECT ID FROM slas where title = 'SLA-003');

INSERT INTO slos(sla_id, title)
  VALUES
  (@SLA001,'SLO-01 of first SLA'),
  (@SLA001,'SLO-02 of first SLA'),
  (@SLA002,'SLO-01 of second SLA'),
  (@SLA002,'SLO-02 of second SLA'),
  (@SLA003,'SLO-01 of third SLA'),
  (@SLA003,'SLO-02 of third SLA');

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM slos;
