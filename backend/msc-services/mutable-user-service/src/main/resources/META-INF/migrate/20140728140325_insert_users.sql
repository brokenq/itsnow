-- // insert_users
-- Migration SQL that makes the change goes here.
INSERT users(username, email, phone, password)
VALUES
('admin',    'admin@itsnow.com',    '13012345678', '54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2'),
('root',     'root@itsnow.com',     '13112345678', 'e81a8a2dc76c227258a1bd5551f3d3f45e21deeec634c052dc4e7acd82c0dd92db674b56d22dd637'),
('monitor',  'monitor@itsnow.com',  '13212345678', 'd73e6f76127849f457d17eb663f3f9605c89d1b9d596a5842e13bbcb56e658bbaceb07b8da9886b8'),
('reporter', 'reporter@itsnow.com', '13312345678', 'cf145bec215522df207511897ee8c581db56ff33c59ad2e2367216c19e84036a7dfa107740f344c5'),
('demo',     'demo@itsnow.com',     '13412345678', '6d5478d48e305f2d83ee64bb19387fa538f338b2b461a1ef0f8d257a1570d159901a84d3875797f3'),
('guest',    'guest@itsnow.com',    '13512345678', 'b80b1ebe266d9f52af07283a7819edda2673cca6a0f9cda9c05d8d31864d5d9793796fadc92f96e4');

-- 以上密码通过 Groovy Console
-- import org.springframework.security.crypto.password.StandardPasswordEncoder
-- passwordEncoder = new StandardPasswordEncoder();
-- passwordEncoder.encode("secret");
-- 方式生成

-- //@UNDO
-- SQL to undo the change goes here.
-- 存在其他表外键引用本表时, 就无法使用 truncate
-- TRUNCATE TABLE users;
DELETE FROM users;-- WHERE username IN ('admin','root','monitor','reporter','demo', 'guest');

