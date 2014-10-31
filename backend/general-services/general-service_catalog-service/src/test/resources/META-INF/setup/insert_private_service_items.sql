DELETE FROM private_service_items;
INSERT INTO private_service_items(id,catalog_id,public_id,sn, title, brief, description, icon)
VALUES
(1,1,1,'sn-1011', 'General Hardware Problem', 'General', 'General problems which can not be concluded into desktop/laptop/printer', '/assets/si/hardware_general.png'),
(2,2,NULL,'sn-3011', 'Desktop Clean', 'Clean Desktop', 'Clean the desktop drivers', NULL);
