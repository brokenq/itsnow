-- // insert public_service_catalogs
-- Migration SQL that makes the change goes here.
DELETE FROM itsnow_msc.public_service_catalogs;
INSERT INTO itsnow_msc.public_service_catalogs(parent_id, sn, title, description, icon)
VALUES
 (NULL, 'SC_100', 'Hardware', 'Services related to hardware purchase, upgrading RAM, requesting a laptop or a desktop', '/assets/sc/hardware.png'),
 (NULL, 'SC_200', 'Internet', 'Services related to internet access, WiFi access, creating a VPN account, booking a domain', '/assets/sc/internet.png'),
 (NULL, 'SC_300', 'Software', 'Services related to software installation/un-installation, software purchase, license ', '/assets/sc/software.png'),
 (NULL, 'SC_400', 'User Accounts', 'Services related to hiring a new employee, requesting a department/place change', '/assets/sc/user-accounts.png'),
 (NULL, 'SC_500', 'Application Login', 'Services related to application login like creating CRM account, SAP account, MSSQL account', '/assets/sc/application-login.png');

SET @SC100 = (SELECT id FROM itsnow_msc.public_service_catalogs where sn = 'SC_100');

INSERT INTO itsnow_msc.public_service_catalogs(parent_id, sn, title, description, icon)
VALUES
  (@SC100, 'SC_101', 'Desktop', 'Services related to desktop computer', '/assets/sc/desktop.png'),
  (@SC100, 'SC_102', 'Laptop', 'Services related to laptop', '/assets/sc/laptop.png'),
  (@SC100, 'SC_103', 'Printer', 'Services related to printer', '/assets/sc/printer.png');

-- //@UNDO
-- SQL to undo the change goes here.

-- delete all records refer to others(maybe fail if there are 3-levels)
DELETE FROM itsnow_msc.public_service_catalogs WHERE parent_id is not null;
DELETE FROM itsnow_msc.public_service_catalogs;
