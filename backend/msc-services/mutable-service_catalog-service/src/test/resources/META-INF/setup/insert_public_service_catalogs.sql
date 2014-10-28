DELETE FROM public_service_catalogs;
INSERT INTO public_service_catalogs(id,parent_id,level, sn, title, description, icon)
VALUES
 (1,NULL,1, 'SC_100', 'Hardware', 'Services related to hardware purchase, upgrading RAM, requesting a laptop or a desktop', '/assets/sc/hardware.png'),
 (2,NULL,1, 'SC_200', 'Internet', 'Services related to internet access, WiFi access, creating a VPN account, booking a domain', '/assets/sc/internet.png'),
 (3,NULL,1, 'SC_300', 'Software', 'Services related to software installation/un-installation, software purchase, license ', '/assets/sc/software.png'),
 (4,NULL,1, 'SC_400', 'User Accounts', 'Services related to hiring a new employee, requesting a department/place change', '/assets/sc/user-accounts.png'),
 (5,NULL,1, 'SC_500', 'Application Login', 'Services related to application login like creating CRM account, SAP account, MSSQL account', '/assets/sc/application-login.png');

INSERT INTO public_service_catalogs(id,parent_id,level, sn, title, description, icon)
VALUES
  (6,1,2, 'SC_101', 'Desktop', 'Services related to desktop computer', '/assets/sc/desktop.png'),
  (7,2,2, 'SC_102', 'Laptop', 'Services related to laptop', '/assets/sc/laptop.png'),
  (8,2,2, 'SC_103', 'Printer', 'Services related to printer', '/assets/sc/printer.png'),
  (9,6,3, 'SC_1011', 'Desktop1', 'Services related to desktop computer1', '/assets/sc/desktop.png'),
  (10,6,3, 'SC_1012', 'Laptop1', 'Services related to laptop1', '/assets/sc/laptop.png');

