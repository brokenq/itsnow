-- // insert private_service_items
-- Migration SQL that makes the change goes here.

SET @SC100 = (SELECT id FROM private_service_catalogs where sn = 'SC_100');
SET @SC101 = (SELECT id FROM private_service_catalogs where sn = 'SC_101');
SET @SC102 = (SELECT id FROM private_service_catalogs where sn = 'SC_102');
SET @SC200 = (SELECT id FROM private_service_catalogs where sn = 'SC_200');
SET @SC300 = (SELECT id FROM private_service_catalogs where sn = 'SC_300');
SET @P001 = (SELECT id FROM private_service_catalogs where sn = 'P_001');
INSERT INTO private_service_items(public_id,catalog_id, title, brief, description, icon)
VALUES
(1,@SC100, 'General Hardware Problem', 'General', 'General problems which can not be concluded into desktop/laptop/printer', '/assets/si/hardware_general.png'),
(2,@SC101, 'Desktop Clean', 'Clean Desktop', 'Clean the desktop drivers', NULL),
(3,@SC102, 'Laptop Maintain', 'Maintain laptop', 'Maintain the laptop', NULL),
(NULL,@P001,'ERP Service','ERP app service','ERP app Service',NULL);
-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM public_service_items;
