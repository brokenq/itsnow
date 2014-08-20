-- // insert_ci_types
-- Migration SQL that makes the change goes here.

# This tree refer to Managed Engine, Service Now, AdRem NetCrunch
#  NetCrunch divide devices into: device-class -> category -> subcategory, and use numbering rule in ID
#  许多NetCrunch里面的subcategory，例如Cisco的各个型号的路由器，不需要映射成为某个具体的ci type，均映射到Router即可
#  这也反映了一点，it基础架构的模型和bsm的服务模型，以及itsm的业务模型，它们并不是一一对应的，而是有个映射和转换的关系
INSERT ci_types(id, name, parent_id, icon_id, description) VALUES
# Business and service
  (1,  'Business Service',                NULL,  1, 'The Business Service'),
  (2,  'Business Process',                NULL,  2, 'The Business Process'),
  (3,  'IT Service',                      NULL,  3, 'The IT Service'),

# Supported organizations
  (10,  'Account',                        NULL,  4, 'The Account'),
  (11,  'MSC Account',                      10,  5, 'The MSC Account'),
  (12,  'MSU Account',                      10,  6, 'The MSU Account'),
  (13,  'MSP Account',                      10,  7, 'The MSP Account'),
  (14,  'Department',                     NULL,  8, 'The Department'),
  (15,  'People',                         NULL,  9, 'The People'),
  (16,  'Requester',                        16, 10, 'The Requester'),
  (17,  'Technician',                       16, 11, 'The Technician'),
  (18,  'Support Group',                  NULL, 12, 'The Support Group'),
  (19,  'Document',                       NULL, 13, 'The Document'),

# Servers: the hardware / computer servers, not the running software，represents Operation System
  (30,  'Computer Server',                NULL, 14, 'The Server'),
  (31,  'Application Server',               30, 15, 'The Application Server'),
  (32,  'Database Server',                  30, 16, 'The Database Server'),
  (33,  'File Server',                      30, 17, 'The File Server'),
  (34,  'Storage Server',                   30, 18, 'The Storage Server'),
  (35,  'IBM Main Frame',                   30, 19, 'The IBM Mainframe'),
  (36,  'Windows Server',                   30, 20, 'The Windows Server'),
  (37,  'Linux Server',                     30, 21, 'The Linux Server'),
  (38,  'Unix Server',                      30, 22, 'The Unix Server'),
  (39,  'AIX Server',                       38, 23, 'The AIX Server'),
  (40,  'HPUX Server',                      38, 24, 'The HP Unix Server'),
  (41,  'FreeBSD Server',                   38, 25, 'The Free BSD Server'),
  (42,  'Mac OSX Server',                   38, 26, 'The Mac OSX Server'),
  (43,  'Netware Server',                   38, 27, 'The Novell Netware Server'),

  (50, 'Virtualization Server',             30, 28, 'The Virtualization Server'),
  (51, 'Hyper-V Server',                    50, 29, 'The Hyper-V Server'),
  (52, 'ESX Server',                        50, 30, 'The ESX Server'),

# Workstations: the computer workstations, not the running software
  (60,  'Workstation',                    NULL, 31, 'The Workstation'),
  (61,  'IBM Workstation',                NULL, 32, 'The IBM Workstation'),
  (62,  'Windows Workstation',            NULL, 33, 'The Windows Workstation'),
  (63,  'Unix Workstation',               NULL, 34, 'The Unix Workstation'),
  (64,  'AIX Workstation',                NULL, 35, 'The AIX Workstation'),
  (65,  'HPUX Workstation',               NULL, 36, 'The HPUX Workstation'),
  (66,  'Linux Workstation',              NULL, 37, 'The Linux Workstation'),
  (67,  'Mac Workstation',                NULL, 38, 'The Mac Workstation'),
  (68,  'Solaris Workstation',            NULL, 39, 'The Solaris Workstation'),

# Software, n-tiers:
#   application/web-server/app-server/database/operation system/virtual machines
# means to runnable|running system
  (80, 'Software',                        NULL, 40, 'The Software'),
  # the application is the top layer
  (81, 'Application',                       80, 41, 'The Application'),
  (82, 'Web Service',                       81, 42, 'The Web Service'),
  (83, 'REST Service',                      81, 43, 'The Rest Service'),
  (84, 'Web Site',                          81, 44, 'The Web Site'),
  # some simple applications run in web server
  (90, 'Web Server',                        80, 45, 'The Web Server'),
  (91, 'Apache',                            90, 46, 'The Apache Web Server'),
  (92, 'IIS',                               90, 47, 'The Microsoft IIS Web Server'),
  (93, 'Nginx',                             90, 48, 'The Nginx Web Server'),
  # some complex applications run in app server
  (100, 'App Server',                       80, 49, 'The APP Server'),
  (101, 'Weblogic',                        100, 50, 'The Weblogic Application Server'),
  (102, 'Websphere',                       100, 51, 'The Websphere Application Server'),
  (103, 'Domino',                          100, 52, 'The Domino Application Server'),
  (104, 'JBoss',                           100, 53, 'The JBoss Application Server'),
  (105, 'Tomcat',                          100, 54, 'The Tomcat Application Server'),
  (106, 'Jetty',                           100, 55, 'The Jetty Application Server'),
  # almost all applications need database
  (110, 'Database',                         80, 56, 'The Database'),
  (111, 'DB2',                             110, 57, 'The DB2 Database'),
  (112, 'MSSQL',                           110, 58, 'The MS SQL Server Database'),
  (113, 'MySQL',                           110, 59, 'The MySQL Database'),
  (114, 'Oracle',                          110, 60, 'The Oracle Database'),
  (115, 'Sybase',                          110, 61, 'The Sybase Database'),
  # and some applications maybe need other infrastructure services
  (120, 'Infrastructure Service',           80, 62, 'The Infrastructure Service'),
  (121, 'Directory Server',                120, 63, 'The Directory Server'),
  (122, 'Email Server',                    120, 64, 'The Email Server'),
  (123, 'FTP Server',                      120, 65, 'The FTP Server'),
  (124, 'LDAP Server',                     120, 66, 'The LDAP Server'),

  # and they maybe runs in virtual machines
  (130, 'Virtual Machine',                  80, 67, 'The Virtual Machine'),
  (131, 'KVM',                             130, 68, 'The KVM Virtual Machine'),
  (132, 'Parallels',                       130, 69, 'The Parallels Virtual Machine'),
  (133, 'VMWare',                          130, 70, 'The VMWare Virtual Machine'),
  (134, 'Citrix XEN',                      130, 71, 'The Citrix XEN Virtual Machine'),

# Virtual Machines includes virtual machine objects

  (140, 'Virtual Machine Object',         NULL, 72, 'The Virtual Machine Object'),
  (141, 'Hyper-V Object',                  140, 73, 'The Hyper-V Object'),
  (142, 'KVM Object',                      140, 74, 'The KVM Object'),

  (143, 'Virtual Machine Instance',        140, 75, 'The Virtual Machine Instance'),
  (150, 'EC2 Virtual Machine Instance',    143, 76, 'The EC2 Virtual Machine Instance'),
  (151, 'Hyper-V Virtual Machine Instance',143, 77, 'The Hyper-V Virtual Machine Instance'),
  (152, 'KVM Virtual Machine Instance',    143, 78, 'The KVM Virtual Machine Instance'),
  (153, 'Solaris Virtual Machine Instance',143, 79, 'The Solaris Virtual Machine Instance'),
  (154, 'VMWare Virtual Machine Instance', 143, 80, 'The VMWare Virtual Machine Instance'),

  (144, 'Virtual Machine Template',        140, 81, 'The Virtual Machine Template'),
  (147, 'VMWare Virtual Machine Template', 144, 82, 'The VMWare Virtual Machine Template'),


  (145, 'VMWare VCenter Object',           140, 83, 'The VMWare VCenter Object'),
  (160, 'ESX Resource Pool',               145, 84, 'The VMWare VCenter Object'),
  (161, 'VMWare VCenter Cluster',          145, 85, 'The VMWare VCenter Object'),
  (162, 'VMWare VCenter DataCenter',       145, 86, 'The VMWare VCenter Object'),
  (163, 'VMWare VCenter DataStore',        145, 87, 'The VMWare VCenter Object'),
  (164, 'VMWare VCenter Folder',           145, 88, 'The VMWare VCenter Object'),
  (165, 'VMWare VCenter Network',          145, 89, 'The VMWare VCenter Object'),

  (146, 'VMWare VCenter Instance',        NULL, 90, 'The VMWare VCenter Instance'),

# Database have DB Schemas(Catalog): which store application data
  (180, 'Database Schema',                NULL, 91, 'The database schema'),
  (181, 'DB2 Schema',                      180, 92, 'The DB2 schema'),
  (182, 'MSSQL Schema',                    180, 93, 'The MSSQL schema'),
  (183, 'MySQL Schema',                    180, 94, 'The MySQL schema'),
  (184, 'Oracle Schema',                   180, 95, 'The Oracle schema'),
  (185, 'Sybase Schema',                   180, 96, 'The Sybase schema'),

# File system support all kinds of software
  (190, 'File System',                    NULL, 97, 'The File System'),
  (191, 'NFS File System',                 190, 98, 'The NFS File System'),
  (192, 'SMB File System',                 190, 99, 'The SMB File System'),

# Service Instances wrapper all kinds of software running as 'computer service'
  (195,  'Service Instance',              NULL, 101, 'The IP Service Instance'),
  (196,  'Unix Daemon',                    195, 102, 'The Unix Daemon'),
  (197,  'Windows Service',                195, 103, 'The Windows Service'),

## Advanced Concepts

# Storage: software need storage
  (200,  'Storage Device',                NULL, 104, 'The Storage Device'),
  (201,  'Storage HBA',                   NULL, 105, 'The Storage HBA'),
  (202,  'Storage Pool',                  NULL, 106, 'The Storage Pool'),
  (203,  'Storage Port',                  NULL, 107, 'The Storage Port'),
  (204,  'Storage Volume',                NULL, 108, 'The Storage Volume'),
  (205,  'Storage File Share',            NULL, 109, 'The Storage File Share'),

# Cluster: software maybe organized as cluster
  (210,  'Cluster',                       NULL, 110, 'The Cluster'),
  (211,  'Cluster Node',                  NULL, 111, 'The Cluster Node'),
  (212,  'Cluster Resource',              NULL, 112, 'The Cluster Resource'),
  (213,  'Cluster Virtual IP',            NULL, 113, 'The Cluster Virtual IP'),

# Load Balancer
  (220,  'Load Balancer Server',          NULL, 114, 'The Load balancer Server'),
  (221,  'Load Balancer Interface',       NULL, 115, 'The Load balancer Interface'),
  (222,  'Load Balancer Pool',            NULL, 116, 'The Load balancer Pool'),
  (223,  'Load Balancer Pool Member',     NULL, 117, 'The Load balancer Pool Member'),
  (224,  'Load Balancer Service',         NULL, 118, 'The Load balancer Service'),
  (225,  'Load Balancer VLAN',            NULL, 119, 'The Load balancer VLAN'),

# IT Data center and room
  (230,  'Data Center',                   NULL, 120, 'The Data Center'),
  (231,  'Data Center Zone',              NULL, 121, 'The Data Center Zone'),
  (232,  'Computer Room',                 NULL, 122, 'The Computer Room'),
  (233,  'Rack',                          NULL, 123, 'The Rack'),

#  UPS
  (240,  'UPS',                           NULL, 124, 'The UPS'),
  (241,  'UPS Input',                     NULL, 125, 'The UPS Input'),
  (242,  'UPS Output',                    NULL, 126, 'The UPS Output'),
  (243,  'UPS Alarm',                     NULL, 127, 'The UPS Alarm'),
  (244,  'UPS Bypass',                    NULL, 128, 'The UPS Bypass'),

# IP Network
  (250,  'Network',                       NULL, 129, 'The IP Network'),
  (251,  'Firewall',                      NULL, 130, 'The Firewall'),
  (252,  'Network Gear',                  NULL, 131, 'The Network Gear'),
  (253,  'Router',                        252,  132, 'The IP Router'),
  (254,  'Switch',                        252,  133, 'The IP Switch'),
  (255,  'Switch Port',                   NULL, 134, 'The Switch Port'),
  (256,  'IP Phone',                      NULL, 135, 'The IP Phone'),
  (257,  'IP Address',                    NULL, 136, 'The IP Address'),
  (258,  'DNS Name',                      NULL, 137, 'The DNS Name'),
  (259,  'IP Device',                     NULL, 138, 'The IP Device'),
  (260,  'VPN',                           NULL, 139, 'The Virtual Private Network'),
  (261,  'Access Point',                  NULL, 140, 'The Access Point'),

# IT Accessory, Computer Peripheral and Assets
  # 附件：可以离开Computer独立使用
  (280,  'Accessory',                     NULL, 141, 'The Accessory'),
  (281,  'Printer',                        280, 142, 'The Printer'),
  (282,  'Smart Phone',                    280, 143, 'The Smart Phone'),
  (283,  'Tablet',                         280, 144, 'The Tablet'),
  # 外设: 附着于Computer使用
  (290,  'Computer Peripheral',           NULL, 145, 'The Computer Peripheral'),
  (291,  'Projector',                      290, 146, 'The Projector'),
  (292,  'Keyboard',                       290, 147, 'The Keyboard'),
  (293,  'Scanner',                        290, 148, 'The Scanner'),
  (294,  'Disk',                           290, 149, 'The Disk'),
  (295,  'Network Adapter',                290, 150, 'The Network Adapter');


-- //@UNDO
-- SQL to undo the change goes here.


TRUNCATE TABLE ci_types;
