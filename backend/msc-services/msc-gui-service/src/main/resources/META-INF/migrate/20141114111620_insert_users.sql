-- // insert_users
-- Migration SQL that makes the change goes here.

-- 由于注册功能有问题影响到集成测试，目前暂时先预先insert msp_test测试数据
SET @account_id = (SELECT id from accounts where sn = 'msp_it_test');
INSERT INTO users(account_id, username, nick_name, email, phone, password) VALUES
  (@account_id, 'msp_it_test', 'MSP Testor For It', 'msp_it_test@itsnow.com',  '13112345678', '54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2');

SET @user_id = (SELECT id from users where username = 'msp_it_test');
UPDATE accounts SET user_id = @user_id WHERE id = @account_id;

-- //@UNDO
-- SQL to undo the change goes here.


