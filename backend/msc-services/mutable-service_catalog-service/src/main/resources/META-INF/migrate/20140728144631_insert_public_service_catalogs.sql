-- // insert public_service_catalogs
-- Migration SQL that makes the change goes here.
INSERT public_service_catalogs(parent_id, sn, level, title, description, icon)
VALUES
 (NULL, 'SC_100', 1,'Hardware', 'Services related to hardware purchase, upgrading RAM, requesting a laptop or a desktop', '/assets/sc/hardware.png'),
 (NULL, 'SC_200', 1,'Internet', 'Services related to internet access, WiFi access, creating a VPN account, booking a domain', '/assets/sc/internet.png'),
 (NULL, 'SC_300', 1,'Software', 'Services related to software installation/un-installation, software purchase, license ', '/assets/sc/software.png'),
 (NULL, 'SC_400', 1,'User Accounts', 'Services related to hiring a new employee, requesting a department/place change', '/assets/sc/user-accounts.png'),
 (NULL, 'SC_500', 1,'Application Login', 'Services related to application login like creating CRM account, SAP account, MSSQL account', '/assets/sc/application-login.png');

SET @SC100 = (SELECT id FROM public_service_catalogs where sn = 'SC_100');

INSERT public_service_catalogs(parent_id, sn, level,title, description, icon)
VALUES
  (@SC100, 'SC_101', 2,'Desktop', 'Services related to desktop computer', '/assets/sc/desktop.png'),
  (@SC100, 'SC_102', 2,'Laptop', 'Services related to laptop', '/assets/sc/laptop.png'),
  (@SC100, 'SC_103', 2,'Printer', 'Services related to printer', '/assets/sc/printer.png');

SET @SC101 = (SELECT id FROM public_service_catalogs where sn = 'SC_101');

INSERT public_service_catalogs(parent_id, sn, level,title, description, icon)
VALUES
  (@SC101, 'SC_1011', 3,'D1', 'D1', '/assets/sc/mobile.png'),
  (@SC101, 'SC_1012', 3,'D2', 'D2', '/assets/sc/mobile.png');

-- //@UNDO
-- SQL to undo the change goes here.

-- delete all records refer to others(maybe fail if there are 3-levels)
DELETE FROM public_service_catalogs WHERE parent_id is not null;
DELETE FROM public_service_catalogs;
