DELETE FROM public_service_items;
INSERT INTO public_service_items(id,catalog_id, title, brief, description, icon)
VALUES
(1,1, 'General Hardware Problem', 'General', 'General problems which can not be concluded into desktop/laptop/printer', '/assets/si/hardware_general.png'),
(2,6, 'Desktop Clean', 'Clean Desktop', 'Clean the desktop drivers', NULL),
(3,7, 'Laptop Maintain', 'Maintain laptop', 'Maintain the laptop', NULL),
(4,8, 'Laptop Env configure', 'Env configure',  'Configure the laptop', NULL);

