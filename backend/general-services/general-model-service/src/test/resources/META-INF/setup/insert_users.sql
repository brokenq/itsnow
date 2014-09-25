-- // insert_users
-- Migration SQL that makes the change goes here.

SET @MSC_ID = (SELECT id from itsnow_msc.accounts where sn = 'msc');
SET @MSU001 = (SELECT id from itsnow_msc.accounts where sn = 'msu_001');
SET @MSU002 = (SELECT id from itsnow_msc.accounts where sn = 'msu_002');
SET @MSP001 = (SELECT id from itsnow_msc.accounts where sn = 'msp_001');
SET @MSP002 = (SELECT id from itsnow_msc.accounts where sn = 'msp_002');


INSERT INTO itsnow_msc.users(account_id, username, nick_name, email, phone, password) VALUES
(@MSC_ID, 'admin',     'Administrator', 'admin@itsnow.com',    '13012345678', '54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2'),
(@MSC_ID, 'root',      'Super Admin',   'root@itsnow.com',     '13112345678', 'e81a8a2dc76c227258a1bd5551f3d3f45e21deeec634c052dc4e7acd82c0dd92db674b56d22dd637'),
(@MSU001, 'steve.li',  'Steven Li',     'steve@csvw.com',      '13212345678', 'd73e6f76127849f457d17eb663f3f9605c89d1b9d596a5842e13bbcb56e658bbaceb07b8da9886b8'),
(@MSU001, 'jason.wang','Jason Wang',    'jason@csvw.com',      '13312345678', 'cf145bec215522df207511897ee8c581db56ff33c59ad2e2367216c19e84036a7dfa107740f344c5'),
(@MSP001, 'stone.xin', 'Stone Xin',     'stone@dnt.com.cn',    '13412345678', '6d5478d48e305f2d83ee64bb19387fa538f338b2b461a1ef0f8d257a1570d159901a84d3875797f3'),
(@MSP001, 'jacky.cao', 'Jacky Cao',     'jacky@dnt.com.cn',    '13512345678', 'b80b1ebe266d9f52af07283a7819edda2673cca6a0f9cda9c05d8d31864d5d9793796fadc92f96e4'),
(@MSP001, 'smile.tian','Smile Tian',    'smile@dnt.com.cn',    '13612345678', 'aa4e6ddfb9544727dfc68f58f86f052e4c7d34f3307b0c7fe6fbc1c9d56bc677eb87aa3165a72c2d'),
(@MSU002, 'sharp.liu', 'Sharp Liu',     'sharp@gm.com',        '13712345678', '86b6132dbf5ebf9eb9227d259626a527f4caf5a92d4bdd6db182306dbbd61c601954028d1184ff3c'),
(@MSU002, 'mike.wei',  'Mile Wei',      'mike@gm.com',         '13812345678', 'f8d016511819edf96fb6dff7e9f012d9f9556f60e756793080e5ff7fa8ea02369aedcc3010c05465'),
(@MSP002, 'rose.zhou', 'Rose Zhou',     'rose@teamsun.com',    '13912345678', '7e579a86efe48d0e3d83a757cee256aef2e70929cbc7ba8eccc9c82469878510860a4c41a9ac650b'),
(@MSP002, 'susie.qian','Susie Qian',    'susie@teamsun.com',   '18912345678', 'd0073ac092e2252c264ffd08132f9331965b88c5150cc2b50739e7e2403d3d2336fb7784859a4434'),
(NULL,    'jay.xiong', 'Jay Xiong',     'jay@kadvin.com',      '18612345678', 'b078a7f871a1de97972bb1441eded5e26930f024d19335b8c2e93f14e50488560057823fc9dbdc2c');

-- 以上密码通过 Groovy Console
-- import org.springframework.security.crypto.password.StandardPasswordEncoder
-- passwordEncoder = new StandardPasswordEncoder();
-- passwordEncoder.encode("secret");
-- 方式生成

-- 更新以上账户的主用户
SET @steven_li_id = (SELECT id FROM itsnow_msc.users where username = 'steve.li');
SET @sharp_liu_id = (SELECT id FROM itsnow_msc.users where username = 'sharp.liu');
SET @jacky_cao_id = (SELECT id FROM itsnow_msc.users where username = 'jacky.cao');
SET @rose_zhou_id = (SELECT id FROM itsnow_msc.users where username = 'rose.zhou');

UPDATE itsnow_msc.accounts SET user_id = @steven_li_id WHERE id = @MSU001;
UPDATE itsnow_msc.accounts SET user_id = @sharp_liu_id WHERE id = @MSU002;
UPDATE itsnow_msc.accounts SET user_id = @jacky_cao_id WHERE id = @MSP001;
UPDATE itsnow_msc.accounts SET user_id = @rose_zhou_id WHERE id = @MSP002;

-- //@UNDO
-- SQL to undo the change goes here.
-- 存在其他表外键引用本表时, 就无法使用 truncate

DELETE FROM itsnow_msc.users;-- WHERE username IN ('admin','root','monitor','reporter','demo', 'guest');

