DELETE FROM public_service_items;
INSERT INTO public_service_items(id,catalog_id,sn, title, brief, description, icon)
VALUES
(1,1,'sn-1011', 'General Hardware Problem', 'General', 'General problems which can not be concluded into desktop/laptop/printer', '/assets/si/hardware_general.png'),
(2,6,'sn-3011', 'Desktop Clean', 'Clean Desktop', 'Clean the desktop drivers', NULL),
(3,7,'sn-3012', 'Laptop Maintain', 'Maintain laptop', 'Maintain the laptop', NULL),
(4,8,'sn-3013', 'Laptop Env configure', 'Env configure',  'Configure the laptop', NULL);

