-- // insert public_service_catalogs
-- Migration SQL that makes the change goes here.
INSERT public_service_catalogs(id,parent_id, sn,level, title, description, icon)
VALUES
 (1,NULL, 'SC_100',1, 'Hardware', 'Services related to hardware purchase, upgrading RAM, requesting a laptop or a desktop', '/assets/sc/hardware.png'),
 (2,NULL, 'SC_200',1, 'Internet', 'Services related to internet access, WiFi access, creating a VPN account, booking a domain', '/assets/sc/internet.png'),
 (3,NULL, 'SC_300',1, 'Software', 'Services related to software installation/un-installation, software purchase, license ', '/assets/sc/software.png'),
 (4,NULL, 'SC_400',1, 'User Accounts', 'Services related to hiring a new employee, requesting a department/place change', '/assets/sc/user-accounts.png'),
 (5,NULL, 'SC_500',1, 'Application Login', 'Services related to application login like creating CRM account, SAP account, MSSQL account', '/assets/sc/application-login.png');

INSERT public_service_catalogs(id,parent_id, sn,level, title, description, icon)
VALUES
  (6,1, 'SC_101',2, 'Desktop', 'Services related to desktop computer', '/assets/sc/desktop.png'),
  (7,1, 'SC_102',2, 'Laptop', 'Services related to laptop', '/assets/sc/laptop.png'),
  (8,1, 'SC_103',2, 'Printer', 'Services related to printer', '/assets/sc/printer.png');

-- //@UNDO
-- SQL to undo the change goes here.

-- delete all records refer to others(maybe fail if there are 3-levels)
DELETE FROM public_service_catalogs WHERE parent_id is not null;
DELETE FROM public_service_catalogs;
