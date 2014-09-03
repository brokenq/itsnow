-- // insert public_service_catalogs
-- Migration SQL that makes the change goes here.
INSERT private_service_catalogs(public_id,parent_id, sn, title, description, icon)
VALUES
 (1,NULL, 'SC_100', 'Hardware', 'Services related to hardware purchase, upgrading RAM, requesting a laptop or a desktop', '/assets/sc/hardware.png'),
 (2,NULL, 'SC_200', 'Internet', 'Services related to internet access, WiFi access, creating a VPN account, booking a domain', '/assets/sc/internet.png'),
 (3,NULL, 'SC_300', 'Software', 'Services related to software installation/un-installation, software purchase, license ', '/assets/sc/software.png');

SET @SC100 = (SELECT id FROM private_service_catalogs where sn = 'SC_100');

INSERT private_service_catalogs(public_id,parent_id, sn, title, description, icon)
VALUES
  (6,@SC100, 'SC_101', 'Desktop', 'Services related to desktop computer', '/assets/sc/desktop.png'),
  (7,@SC100, 'SC_102', 'Laptop', 'Services related to laptop', '/assets/sc/laptop.png'),
  (NULL,NULL,'P_001','Private','Private Service Catalog','assets/psc/private.png');

-- //@UNDO
-- SQL to undo the change goes here.

-- delete all records refer to others(maybe fail if there are 3-levels)
DELETE FROM private_service_catalogs WHERE parent_id is not null;
DELETE FROM private_service_catalogs;
