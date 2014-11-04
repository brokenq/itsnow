-- // insert public_service_items
-- Migration SQL that makes the change goes here.

INSERT INTO public_service_items(id,sn,catalog_id, title, brief, description, icon)
VALUES
(1,3, 'General Software Problem', 'General', 'General problems which can not be concluded into desktop/laptop/printer', '/assets/si/hardware_general.png'),
(2,6, 'Desktop Clean', 'Clean Desktop', 'Clean the desktop drivers', NULL),
(3,7, 'Laptop Maintain', 'Maintain laptop', 'Maintain the laptop', NULL),
(4,7, 'Laptop Env configure', 'Env configure',  'Configure the laptop', NULL);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM public_service_items;
