INSERT INTO `ACT_ID_GROUP` (`ID_`,`REV_`,`NAME_`,`TYPE_`) VALUES ('admin',1,'Admin','security-role');
INSERT INTO `ACT_ID_GROUP` (`ID_`,`REV_`,`NAME_`,`TYPE_`) VALUES ('engineering',1,'Engineering','assignment');
INSERT INTO `ACT_ID_GROUP` (`ID_`,`REV_`,`NAME_`,`TYPE_`) VALUES ('management',1,'Management','assignment');
INSERT INTO `ACT_ID_GROUP` (`ID_`,`REV_`,`NAME_`,`TYPE_`) VALUES ('marketing',1,'Marketing','assignment');
INSERT INTO `ACT_ID_GROUP` (`ID_`,`REV_`,`NAME_`,`TYPE_`) VALUES ('sales',1,'Sales','assignment');
INSERT INTO `ACT_ID_GROUP` (`ID_`,`REV_`,`NAME_`,`TYPE_`) VALUES ('user',1,'User','security-role');

INSERT INTO `ACT_ID_USER` (`ID_`,`REV_`,`FIRST_`,`LAST_`,`EMAIL_`,`PWD_`,`PICTURE_ID_`) VALUES ('admin',1,'jie','cao','jacky_cao@dnt.com.cn','admin',NULL);
INSERT INTO `ACT_ID_USER` (`ID_`,`REV_`,`FIRST_`,`LAST_`,`EMAIL_`,`PWD_`,`PICTURE_ID_`) VALUES ('fozzie',2,'Fozzie','Bear','fozzie@activiti.org','fozzie','22');
INSERT INTO `ACT_ID_USER` (`ID_`,`REV_`,`FIRST_`,`LAST_`,`EMAIL_`,`PWD_`,`PICTURE_ID_`) VALUES ('gonzo',2,'Gonzo','The Great','gonzo@activiti.org','gonzo','18');
INSERT INTO `ACT_ID_USER` (`ID_`,`REV_`,`FIRST_`,`LAST_`,`EMAIL_`,`PWD_`,`PICTURE_ID_`) VALUES ('kermit',2,'Kermit','The Frog','kermit@activiti.org','kermit','7');

INSERT INTO `ACT_ID_INFO` (`ID_`,`REV_`,`USER_ID_`,`TYPE_`,`KEY_`,`VALUE_`,`PASSWORD_`,`PARENT_ID_`) VALUES ('10',1,'kermit','userinfo','location','Hollywoord',NULL,NULL);
INSERT INTO `ACT_ID_INFO` (`ID_`,`REV_`,`USER_ID_`,`TYPE_`,`KEY_`,`VALUE_`,`PASSWORD_`,`PARENT_ID_`) VALUES ('11',1,'kermit','userinfo','phone','+123456789',NULL,NULL);
INSERT INTO `ACT_ID_INFO` (`ID_`,`REV_`,`USER_ID_`,`TYPE_`,`KEY_`,`VALUE_`,`PASSWORD_`,`PARENT_ID_`) VALUES ('12',1,'kermit','userinfo','twitterName','alfresco',NULL,NULL);
INSERT INTO `ACT_ID_INFO` (`ID_`,`REV_`,`USER_ID_`,`TYPE_`,`KEY_`,`VALUE_`,`PASSWORD_`,`PARENT_ID_`) VALUES ('13',1,'kermit','userinfo','skype','activiti_kermit_frog',NULL,NULL);
INSERT INTO `ACT_ID_INFO` (`ID_`,`REV_`,`USER_ID_`,`TYPE_`,`KEY_`,`VALUE_`,`PASSWORD_`,`PARENT_ID_`) VALUES ('8',1,'kermit','userinfo','birthDate','10-10-1955',NULL,NULL);
INSERT INTO `ACT_ID_INFO` (`ID_`,`REV_`,`USER_ID_`,`TYPE_`,`KEY_`,`VALUE_`,`PASSWORD_`,`PARENT_ID_`) VALUES ('9',1,'kermit','userinfo','jobTitle','Muppet',NULL,NULL);

INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('admin','admin');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('kermit','admin');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('fozzie','engineering');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('kermit','engineering');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('gonzo','management');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('kermit','management');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('fozzie','marketing');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('gonzo','marketing');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('kermit','marketing');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('gonzo','sales');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('kermit','sales');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('fozzie','user');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('gonzo','user');
INSERT INTO `ACT_ID_MEMBERSHIP` (`USER_ID_`,`GROUP_ID_`) VALUES ('kermit','user');



-- //@UNDO