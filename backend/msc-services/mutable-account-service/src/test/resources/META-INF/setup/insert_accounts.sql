-- SET SCHEMA itsnow_msc;

INSERT INTO itsnow_msc.accounts(id, sn, name, domain, type, status)
VALUES
 (1, 'msc',     'Itsnow Carrier', 'www',     'msc', 'Valid'),
 (2, 'msu_001', 'Shanghai VW',    'csvw',    'msu', 'Valid'),
 (3, 'msu_002', 'Shanghai GM',    'csgm',    'msu', 'New'),
 (4, 'msp_001', 'DNT',            'dnt',     'msp', 'Valid'),
 (5, 'msp_002', 'TeamSun',        'teamsun', 'msp', 'Rejected');