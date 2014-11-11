-- // insert_accounts
-- Migration SQL that makes the change goes here.

INSERT INTO accounts(id, sn, name, domain, type, status)
VALUES
  (1, 'msc', 'Itsnow Carrier',          'msc', 'msc', 'Valid'),
  (2, 'msu', 'Itsnow Enterprise User',  'msu', 'msu', 'Valid'),
  (3, 'msp', 'Itsnow Service Provider', 'msp', 'msp', 'Valid'),
  -- 由于注册功能有问题影响到集成测试，目前暂时先预先insert msp_test测试数据
  (4, 'msp_test', 'Itsnow Service Provider Testor', 'msp_test', 'msp', 'New')
;


-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM accounts;
