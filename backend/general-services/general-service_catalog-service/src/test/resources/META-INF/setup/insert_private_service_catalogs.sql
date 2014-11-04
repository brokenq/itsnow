INSERT INTO private_service_catalogs(id,public_id,parent_id, sn,level, title, description, icon)
VALUES
 (1,1,NULL, 'SC_100',1, 'Hardware', 'Services related to hardware purchase, upgrading RAM, requesting a laptop or a desktop', '/assets/sc/hardware.png'),
 (2,2,NULL, 'SC_200',1, 'Internet', 'Services related to internet access, WiFi access, creating a VPN account, booking a domain', '/assets/sc/internet.png'),
 (3,3,NULL, 'SC_300',1, 'Software', 'Services related to software installation/un-installation, software purchase, license ', '/assets/sc/software.png'),
 (4,6,1, 'SC_101',2, 'Desktop', 'Services related to desktop computer', '/assets/sc/desktop.png'),
  (5,7,1, 'SC_102',2, 'Laptop', 'Services related to laptop', '/assets/sc/laptop.png'),
  (6,NULL,NULL,'P_001',1,'Private','Private Service Catalog','assets/psc/private.png');
