-- // insert_ci_types
-- Migration SQL that makes the change goes here.

# This tree refer to Managed Engine, Service Now, AdRem NetCrunch
#  NetCrunch divide devices into: device-class -> category -> subcategory, and use numbering rule in ID
#  许多NetCrunch里面的subcategory，例如Cisco的各个型号的路由器，不需要映射成为某个具体的ci type，均映射到Router即可
#  这也反映了一点，it基础架构的模型和bsm的服务模型，以及itsm的业务模型，它们并不是一一对应的，而是有个映射和转换的关系
INSERT ci_types(id, name, parent_id, icon, description) VALUES
# Business and service
  (1,  'Business Service', NULL, 'icons/business_service.png',     'The Business Service'),
  (2,  'Business Process', NULL, 'icons/business_process.png',     'The Business Process'),
  (3,  'IT Service',       NULL, 'icons/it_service.png',    'The IT Service'),

# Supported organizations
  (10,  'Account',             NULL, 'icons/account.png',      'The Account'),
  (11,  'MSC Account',         2,    'icons/msc.png',          'The MSC Account'),
  (12,  'MSU Account',         2,    'icons/msu.png',          'The MSU Account'),
  (13,  'MSP Account',         2,    'icons/msp.png',          'The MSP Account'),
  (14,  'Department',          NULL, 'icons/department.png',   'The Department'),
  (15,  'People',              NULL, 'icons/people.png',       'The People'),
  (16,  'Requester',           NULL, 'icons/requester.png',    'The Requester'),
  (17,  'Technician',          NULL, 'icons/technician.png',   'The Technician'),
  (18,  'Support Group',       NULL, 'icons/support_group.png',   'The Support Group'),
  (19,  'Document',            NULL, 'icons/document.png',     'The Document'),

# Servers: the hardware / computer servers, not the running software，represents Operation System
  (30,  'Computer Server',              NULL, 'icons/server.png',       'The Server'),
  (31,  'Application Server',     8, 'icons/app_server.png',      'The Application Server'),
  (32, 'Database Server',        8, 'icons/db_server.png',       'The Database Server'),
  (33, 'File Server',            8, 'icons/file_server.png',       'The File Server'),
  (34, 'Storage Server',         8, 'icons/storage_server.png',       'The Storage Server'),
  (35, 'IBM Main Frame',         8, 'icons/ibm_mainframe.png',       'The IBM Mainframe'),
  (36, 'Windows Server',         8, 'icons/windows_server.png',       'The Windows Server'),
  (37, 'Linux Server',          14, 'icons/linux_server.png',       'The Linux Server'),
  (38, 'Unix Server',            8, 'icons/unix_server.png',       'The Unix Server'),
  (39, 'AIX Server',            14, 'icons/aix_server.png',       'The AIX Server'),
  (40, 'HPUX Server',           14, 'icons/hpux_server.png',       'The HP Unix Server'),
  (41, 'FreeBSD Server',           14, 'icons/free_bsd_server.png',       'The Free BSD Server'),
  (42, 'Mac OSX Server',            14, 'icons/mac_server.png',       'The Mac OSX Server'),
  (43, 'Netware Server',        14, 'icons/netware_server.png',       'The Novell Netware Server'),

  (50, 'Virtualization Server', 14, 'icons/virtualization_server.png',       'The Virtualization Server'),
  (51, 'Hyper-V Server',        14, 'icons/hyper_v_server.png',       'The Hyper-V Server'),
  (52, 'ESX Server',            14, 'icons/esx_server.png',       'The ESX Server'),

# Workstations: the computer workstations, not the running software
  (60,  'Workstation',         NULL, 'icons/workstation.png',       'The Workstation'),
  (61,  'IBM Workstation',     NULL, 'icons/ibm_workstation.png',       'The IBM Workstation'),
  (62,  'Windows Workstation', NULL, 'icons/windows_workstation.png',       'The Windows Workstation'),
  (63,  'Unix Workstation',    NULL, 'icons/unix_workstation.png',       'The Unix Workstation'),
  (64,  'AIX Workstation',     NULL, 'icons/aix_workstation.png',       'The AIX Workstation'),
  (65,  'HPUX Workstation',    NULL, 'icons/hpux_workstation.png',       'The HPUX Workstation'),
  (66,  'Linux Workstation',   NULL, 'icons/linux_workstation.png',       'The Linux Workstation'),
  (67,  'Mac Workstation',     NULL, 'icons/mac_workstation.png',       'The Mac Workstation'),
  (68,  'Solaris Workstation', NULL, 'icons/solaris_workstation.png',       'The Solaris Workstation'),

# Software, n-tiers:
#   application/web-server/app-server/database/operation system/virtual machines
# means to runnable|running system
  (80, 'Software',            NULL, 'icons/software.png',       'The Software'),
  # the application is the top layer
  (81, 'Application',         NULL, 'icons/application.png',       'The Application'),
  (82, 'Web Service',         NULL, 'icons/web_service.png',       'The Web Service'),
  (83, 'REST Service',        NULL, 'icons/rest_service.png',       'The Rest Service'),
  (84, 'Web Site',            NULL, 'icons/web_site.png',       'The Web Site'),
  # some simple applications run in web server
  (90, 'Web Server',            18, 'icons/web_server.png',       'The Web Server'),
  (91, 'Apache',                18, 'icons/apache.png',       'The Apache Web Server'),
  (92, 'IIS',                   18, 'icons/iis.png',       'The Microsoft IIS Web Server'),
  (93, 'Nginx',                   18, 'icons/nginx.png',       'The Nginx Web Server'),
  # some complex applications run in app server
  (100, 'App Server',            18, 'icons/app_server.png',       'The APP Server'),
  (101, 'Weblogic',              18, 'icons/weblogic.png',       'The Weblogic Application Server'),
  (102, 'Websphere',             18, 'icons/websphere.png',       'The Websphere Application Server'),
  (103, 'Domino',                18, 'icons/domino.png',       'The Domino Application Server'),
  (104, 'JBoss',                 18, 'icons/jboss.png',       'The JBoss Application Server'),
  (105, 'Tomcat',                18, 'icons/tomcat.png',       'The Tomcat Application Server'),
  (106, 'Jetty',                 18, 'icons/jetty.png',       'The Jetty Application Server'),
  # almost all applications need database
  (110, 'Database',            NULL, 'icons/database.png',       'The Database'),
  (111, 'DB2',                   18, 'icons/db2.png',       'The DB2 Database'),
  (112, 'MSSQL',                 18, 'icons/mssql.png',       'The MS SQL Server Database'),
  (113, 'MySQL',                 18, 'icons/mysql.png',       'The MySQL Database'),
  (114, 'Oracle',                18, 'icons/oracle.png',       'The Oracle Database'),
  (115, 'Sybase',                18, 'icons/sybase.png',       'The Sybase Database'),
  # and some applications maybe need other infrastructure services
  (120, 'Infrastructure Service',18, 'icons/infrastructure_service.png',       'The Infrastructure Service'),
  (121, 'Directory Server',      18, 'icons/directory_server.png',       'The Directory Server'),
  (122, 'Email Server',          18, 'icons/email_server.png',       'The Email Server'),
  (123, 'FTP Server',            18, 'icons/ftp_server.png',       'The FTP Server'),
  (124, 'LDAP Server',           18, 'icons/ldap_server.png',       'The LDAP Server'),

  # and they maybe runs in virtual machines
  (130, 'Virtual Machine',       18, 'icons/vm.png',       'The Virtual Machine'),
  (131, 'KVM',                   18, 'icons/kvm.png',       'The KVM Virtual Machine'),
  (132, 'Parallels',             18, 'icons/parallels.png',       'The Parallels Virtual Machine'),
  (133, 'VMWare',                18, 'icons/vmware.png',       'The VMWare Virtual Machine'),
  (134, 'Citrix XEN',            18, 'icons/citrix.png',       'The Citrix XEN Virtual Machine'),

# Virtual Machines includes virtual machine objects

  (140, 'Virtual Machine Object',           18, 'icons/vm_object.png',       'The Virtual Machine Object'),
  (141, 'Hyper-V Object',                   18, 'icons/hyper_v_vm_object.png',       'The Hyper-V Object'),
  (142, 'KVM Object',                       18, 'icons/kvm_vm_object.png',       'The KVM Object'),

  (143, 'Virtual Machine Instance',         18, 'icons/vm_instance.png',       'The Virtual Machine Instance'),
  (150, 'EC2 Virtual Machine Instance',     18, 'icons/ec2_vm_instance.png',       'The EC2 Virtual Machine Instance'),
  (151, 'Hyper-V Virtual Machine Instance', 18, 'icons/hyper_v_vm_instance.png',       'The Hyper-V Virtual Machine Instance'),
  (152, 'KVM Virtual Machine Instance',     18, 'icons/kvm_vm_instance.png',       'The KVM Virtual Machine Instance'),
  (153, 'Solaris Virtual Machine Instance', 18, 'icons/solaris_vm_instance.png',       'The Solaris Virtual Machine Instance'),
  (154, 'VMWare Virtual Machine Instance',  18, 'icons/vmware_vm_instance.png',       'The VMWare Virtual Machine Instance'),

  (144, 'Virtual Machine Template',         18, 'icons/vm_template.png',       'The Virtual Machine Template'),
  (147, 'VMWare Virtual Machine Template',  18, 'icons/vmware_vm_template.png',       'The VMWare Virtual Machine Template'),


  (145, 'VMWare VCenter Object',            18, 'icons/vcenter_object.png',       'The VMWare VCenter Object'),
  (160, 'ESX Resource Pool',                18, 'icons/esx_resource_pool.png',       'The VMWare VCenter Object'),
  (161, 'VMWare VCenter Cluster',           18, 'icons/vcenter_cluster.png',       'The VMWare VCenter Object'),
  (162, 'VMWare VCenter DataCenter',        18, 'icons/vcenter_data_center.png',       'The VMWare VCenter Object'),
  (163, 'VMWare VCenter DataStore',         18, 'icons/vcenter_data_store.png',       'The VMWare VCenter Object'),
  (164, 'VMWare VCenter Folder',            18, 'icons/vcenter_folder.png',       'The VMWare VCenter Object'),
  (165, 'VMWare VCenter Network',           18, 'icons/vcenter_network.png',       'The VMWare VCenter Object'),

  (146, 'VMWare VCenter Instance',          18, 'icons/vcenter.png',       'The VMWare VCenter Instance'),

# Database have DB Schemas(Catalog): which store application data
  (180, 'Database Schema',       18, 'icons/db_schema.png', 'The database schema'),
  (181, 'DB2 Schema',            18, 'icons/db2_schema.png', 'The DB2 schema'),
  (182, 'MSSQL Schema',          18, 'icons/mssql_schema.png', 'The MSSQL schema'),
  (183, 'MySQL Schema',          18, 'icons/mysql_schema.png', 'The MySQL schema'),
  (184, 'Oracle Schema',         18, 'icons/oracle_schema.png', 'The Oracle schema'),
  (185, 'Sybase Schema',         18, 'icons/sybase_schema.png', 'The Sybase schema'),

# File system support all kinds of software
  (190, 'File System',           18, 'icons/file_system.png', 'The File System'),
  (191, 'NFS File System',       18, 'icons/nfs_file_system.png', 'The NFS File System'),
  (192, 'SMB File System',       18, 'icons/smb_file_system.png', 'The SMB File System'),

# Service Instances wrapper all kinds of software running as 'computer service'
  (195,  'Service Instance', NULL, 'icons/ip_service_instance.png',    'The IP Service Instance'),
  (196,  'Unix Daemon',      NULL, 'icons/unix_daemon.png',    'The Unix Daemon'),
  (197,  'Windows Service',  NULL, 'icons/windows_service.png',    'The Windows Service'),

## Advanced Concepts

# Storage: software need storage
  (200,  'Storage Device',      NULL, 'icons/storage.png',      'The Storage Device'),
  (201,  'Storage HBA',         NULL, 'icons/storage_hba.png',      'The Storage HBA'),
  (202,  'Storage Pool',        NULL, 'icons/storage_pool.png',      'The Storage Pool'),
  (203,  'Storage Port',        NULL, 'icons/storage_port.png',      'The Storage Port'),
  (204,  'Storage Volume',      NULL, 'icons/storage_volume.png',      'The Storage Volume'),
  (205,  'Storage File Share',  NULL, 'icons/storage_file_share.png',      'The Storage File Share'),

# Cluster: software maybe organized as cluster
  (210,  'Cluster',             NULL, 'icons/cluster.png',      'The Cluster'),
  (211,  'Cluster Node',        NULL, 'icons/cluster_node.png',      'The Cluster Node'),
  (212,  'Cluster Resource',    NULL, 'icons/cluster_resource.png',      'The Cluster Resource'),
  (213,  'Cluster Virtual IP',  NULL, 'icons/cluster_virtual_ip.png',      'The Cluster Virtual IP'),

# Load Balancer
  (220,  'Load Balancer Server',  NULL, 'icons/lb_server.png',      'The Load balancer Server'),
  (221,  'Load Balancer Interface',  NULL, 'icons/lb_interface.png',      'The Load balancer Interface'),
  (222,  'Load Balancer Pool',  NULL, 'icons/lb_pool.png',      'The Load balancer Pool'),
  (223,  'Load Balancer Pool Member',  NULL, 'icons/lb_pool_member.png',      'The Load balancer Pool Member'),
  (224,  'Load Balancer Service',  NULL, 'icons/lb_service.png',      'The Load balancer Service'),
  (225,  'Load Balancer VLAN',  NULL, 'icons/lb_vlan.png',      'The Load balancer VLAN'),

# IT Data center and room
  (230,  'Data Center',         NULL, 'icons/data_center.png',  'The Data Center'),
  (231,  'Data Center Zone',    NULL, 'icons/data_center_zone.png',  'The Data Center Zone'),
  (232,  'Computer Room',       NULL, 'icons/computer_room.png',  'The Computer Room'),
  (233,  'Rack',                NULL, 'icons/rack.png',         'The Rack'),

#  UPS
  (240,  'UPS',                 NULL, 'icons/ups.png',      'The UPS'),
  (241,  'UPS Input',           NULL, 'icons/ups.png',      'The UPS'),
  (242,  'UPS Output',          NULL, 'icons/ups.png',      'The UPS'),
  (243,  'UPS Alarm',           NULL, 'icons/ups.png',      'The UPS'),
  (244,  'UPS Bypass',          NULL, 'icons/ups.png',      'The UPS'),

# IP Network
  (250,  'Network',             NULL, 'icons/network.png',      'The IP Network'),
  (251,  'Firewall',            NULL, 'icons/firewall.png',     'The Firewall'),
  (252,  'Network Gear',        NULL, 'icons/network_gear.png',  'The Network Gear'),
  (253,  'Router',              NULL, 'icons/router.png',       'The IP Router'),
  (254,  'Switch',              NULL, 'icons/switch.png',       'The IP Switch'),
  (255,  'Switch Port',         NULL, 'icons/switch_port.png',  'The Switch Port'),
  (256,  'IP Phone',            NULL, 'icons/ip_phone.png',    'The IP Phone'),
  (257,  'IP Address',          NULL, 'icons/ip_address.png',    'The IP Address'),
  (258,  'DNS Name',            NULL, 'icons/dns_name.png',    'The DNS Name'),
  (259,  'IP Device',           NULL, 'icons/ip_device.png',    'The IP Device'),
  (260,  'VPN',                 NULL, 'icons/vpn.png',    'The Virtual Private Network'),
  (261,  'Access Point',        NULL, 'icons/access_point.png', 'The Access Point'),

# IT Accessory, Computer Peripheral and Assets
  # 附件：可以离开Computer独立使用
  (280,  'Accessory',          NULL, 'icons/accessory.png',      'The Accessory'),
  (281,  'Printer',             NULL, 'icons/printer.png',      'The Printer'),
  (282,  'Smart Phone',         NULL, 'icons/smart_phone.png',    'The Smart Phone'),
  (283,  'Tablet',              NULL, 'icons/tablet.png',    'The Tablet'),
  # 外设: 附着于Computer使用
  (290,  'Computer Peripheral',          NULL, 'icons/peripheral.png',      'The Computer Peripheral'),
  (291,  'Projector',           NULL, 'icons/projector.png',    'The Projector'),
  (292,  'Keyboard',            NULL, 'icons/keyboard.png',     'The Keyboard'),
  (293,  'Scanner',             NULL, 'icons/scanner.png',      'The Scanner'),
  (294,  'Disk',                NULL, 'icons/disk.png',      'The Disk'),
  (295,  'Network Adapter',     NULL, 'icons/network_adapter.png',      'The Network Adapter');


-- //@UNDO
-- SQL to undo the change goes here.


TRUNCATE TABLE ci_types;
