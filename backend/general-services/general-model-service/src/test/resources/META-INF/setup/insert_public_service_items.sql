-- // insert public_service_items
-- Migration SQL that makes the change goes here.
DELETE FROM public_service_items;
SET @SC100 = (SELECT id FROM public_service_catalogs where sn = 'SC_100');
SET @SC101 = (SELECT id FROM public_service_catalogs where sn = 'SC_101');
SET @SC102 = (SELECT id FROM public_service_catalogs where sn = 'SC_102');
SET @SC200 = (SELECT id FROM public_service_catalogs where sn = 'SC_200');
SET @SC300 = (SELECT id FROM public_service_catalogs where sn = 'SC_300');
SET @SC400 = (SELECT id FROM public_service_catalogs where sn = 'SC_400');
SET @SC500 = (SELECT id FROM public_service_catalogs where sn = 'SC_500');

INSERT INTO public_service_items(catalog_id, title, brief, description, icon)
VALUES
(@SC100, 'General Hardware Problem', 'General', 'General problems which can not be concluded into desktop/laptop/printer', '/assets/si/hardware_general.png'),
(@SC101, 'Desktop Clean', 'Clean Desktop', 'Clean the desktop drivers', NULL),
(@SC102, 'Laptop Maintain', 'Maintain laptop', 'Maintain the laptop', NULL),
(@SC101, 'Laptop Env configure', 'Env configure',  'Configure the laptop', NULL);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM public_service_items;
