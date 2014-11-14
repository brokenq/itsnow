-- // insert_users
-- Migration SQL that makes the change goes here.


INSERT INTO users(id, account_id, username, nick_name, email, phone, password) VALUES
(1, 1, 'admin',     'Administrator',     'admin@itsnow.com',      '13012345678', '54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2'),
(2, 2, 'msu_admin', 'MSU Administrator', 'msu_admin@itsnow.com',  '13112345678', '54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2'),
(3, 3, 'msp_admin', 'MSP Administrator', 'msp_admin@itsnow.com',  '13212345678', '54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2'),
(4, 2, 'msu_user_one', 'MSU User Line One', 'msu_one@itsnow.com',  '13112345678', '54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2'),
(5, 2, 'msu_user_two', 'MSU User Line Two', 'msu_two@itsnow.com',  '13112345678', '54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2'),
(6, 3, 'msp_user_one', 'MSP User Line One', 'msp_one@itsnow.com',  '13112345678', '54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2'),
(7, 3, 'msp_user_two', 'MSP User Line Two', 'msp_two@itsnow.com',  '13112345678', '54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2'),
(8, 3, 'msp_user_desk', 'MSP User Service Desk', 'msp_servicedesk@itsnow.com',  '13112345678', '54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2');

-- 以上密码通过 Groovy Console
-- import org.springframework.security.crypto.password.StandardPasswordEncoder
-- passwordEncoder = new StandardPasswordEncoder();
-- passwordEncoder.encode("secret");
-- 方式生成

-- 更新以上账户的主用户

UPDATE accounts SET user_id = 1 WHERE id = 1;
UPDATE accounts SET user_id = 2 WHERE id = 2;
UPDATE accounts SET user_id = 3 WHERE id = 3;

-- //@UNDO
-- SQL to undo the change goes here.
-- 存在其他表外键引用本表时, 就无法使用 truncate
-- TRUNCATE TABLE users;
DELETE FROM users;

