-- // insert_accounts
-- Migration SQL that makes the change goes here.

-- 由于注册功能有问题影响到集成测试，目前暂时先预先insert msp_test测试数据
INSERT INTO accounts(sn, name, domain, type, status) VALUES
  ('msp_it_test', 'Itsnow Service Provider For It Test', 'msp_it_test', 'msp', 'New');

-- //@UNDO
-- SQL to undo the change goes here.


