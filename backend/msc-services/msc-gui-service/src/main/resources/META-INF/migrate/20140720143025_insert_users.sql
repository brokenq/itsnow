-- // insert_users
-- Migration SQL that makes the change goes here.
SET @MSC_ID = (SELECT id from accounts where sn = 'msc');


INSERT INTO users(account_id, username, nick_name, email, phone, password) VALUES
(@MSC_ID, 'admin',     'Administrator', 'admin@itsnow.com',    '13012345678', '54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2');
-- 以上密码通过 Groovy Console
-- import org.springframework.security.crypto.password.StandardPasswordEncoder
-- passwordEncoder = new StandardPasswordEncoder();
-- passwordEncoder.encode("secret");
-- 方式生成

-- 更新以上账户的主用户
SET @admin_id = (SELECT id FROM users where username = 'admin');

UPDATE accounts SET user_id = @admin_id WHERE id = @MSC_ID;

-- //@UNDO
-- SQL to undo the change goes here.
-- 存在其他表外键引用本表时, 就无法使用 truncate
-- TRUNCATE TABLE users;
DELETE FROM users;

