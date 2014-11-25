DELETE FROM itsnow_msc.accounts;

INSERT INTO itsnow_msc.accounts (sn, name, domain, type, status)
VALUES
  ('msc', 'Itsnow Carrier', 'www', 'msc', 'Valid'),
  ('msu_001', 'Shanghai VW', 'csvw', 'msu', 'Valid'),
  ('msu_002', 'Shanghai GM', 'csgm', 'msu', 'New'),
  ('msp_001', 'DNT', 'dnt', 'msp', 'Valid'),
  ('msp_002', 'TeamSun', 'teamsun', 'msp', 'Rejected');
