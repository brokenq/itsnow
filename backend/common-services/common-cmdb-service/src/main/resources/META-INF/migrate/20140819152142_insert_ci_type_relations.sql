-- // insert_ci_type_relations
-- Migration SQL that makes the change goes here.

INSERT INTO ci_type_relations(ci_type_a_id, ci_relation_type_id, ci_type_b_id) VALUES
# Business Service(1)
#   is enabled by(-15)     IT Service(3)
#   supported by(-10)      Support Group(18)
#   managed by(22)         Technician(17)
#   hosted on(14)          Computer Server(30)
#   receives data from(-4) Software(80)
  (  3, 15,   1),
  ( 18, 10,   1),
  (  1, 22,  17),
  (  1, 14,  30),
  ( 80,  4,   1),

# IT Service(3)
#   enables(15)            Business Service(1)
#   supported by(-10)      Support Group(18)
#   managed by(22)         Technician(17)
#   hosted on(14)          Computer Server(30)
#   receives data from(-4) Application(81)
#   uses(2)                Software(80)
  ( 18, 10,   3),
  (  3, 22,  17),
  (  3, 14,  30),
  ( 81,  4,   3),
  (  3,  2,  80),

# Business Process(2)
#   member of(-16) Business Service(1)
  (  1, 16,   2),

# Account(10)
#   includes(16) People(15)
  ( 10, 16,  15),

# Department(14)
#   includes(16) People(15)
  ( 14, 16,  15),

# People(14)
#   uses(2)       Computer Server(30)
#   uses(2)       Workstation(60)
#   member of(16) Account(10)
#   member of(16) Department(14)
  ( 14, 2,  30),
  ( 14, 2,  60),

# Requester(16)

# Technician(17)
#   are part of(-11) Support Group(18)
#   editor of(-13)   Document(19)
#   author of(12)    Document(19)
#   manages(-22)     Network(250)
#   manages(-22)     Software(80)
#   manages(-22)     Switch(254)
#   manages(-22)     Router(253)
#   manages(-22)     Storage Device(200)
#   manages(-22)     Computer Server(30)
#   manages(-22)     Workstation(60)
#   manages(-22)     Access Point(261)
#   manages(-22)     Business Service(1)
#   manages(-22)     IT Service(3)
  ( 19, 13,  17),
  ( 17, 12,  19),
  ( 80, 22,  17),
  (254, 22,  17),
  (253, 22,  17),
  (200, 22,  17),
  ( 30, 22,  17),
  ( 60, 22,  17),
  (261, 22,  17),

# Support Group(18)
#   consists(11) of Technician(17)
#   supports(10) of Business Service(1)
#   supports(10) of IT Service(3)
#   supports(10) of Network(250)
#   supports(10) of Switch(254)
#   supports(10) of Router(253)
#   supports(10) of Computer Server(30)
  ( 18, 10, 250),
  ( 18, 10, 254),
  ( 18, 10, 253),

# Document(19)
#   is written by Technician(17)
#   is edited  by Technician(17)
#

# Computer Server(30)
#   hosts(14)                Business Service(1)
#   hosts(14)                IT Service(1)
#   supported by(-10)        Support Group(18)
#   in rack(-17)             Rack(233)
#   depends on(1)            Router(253)
#   backed up by(21)         Computer Server(30)
#   located in(19)           Data Center(230)
#   managed by(22)           Technician(17)
#   exchanges data with(20)  Storage Device(200)
#   connected to(6)          Access Point(261)
#   connected to(6)          Switch Port(255)
#   member  of(-18)          Cluster(210)
#   member  of(-16)          Network(250)
#   runs(5)                  Software(80)
#   used by(-2)              People(14)
  (233, 17,  30),
  ( 30,  1, 253),
  ( 30, 21,  30),
  ( 30, 19, 230),
  ( 30, 20, 200),
  ( 30,  6, 261),
  ( 30,  6, 255),
  (210, 18,  30),
  (250, 16,  30),
  ( 30,  5,  80),

# Workstation(60)
#   exchanges data with(20)  Storage Device(200)
#   connected to(6)          Printer(281)
#   connected to(6)          Access Point(261)
#   managed by(22)           Technician(17)
#   member of(-16)           Network(250)
#   runs(5)                  Software(80)
#   used by(-2)              People(14)
  ( 60, 20, 200),
  ( 60,  6, 281),
  ( 60,  6, 261),
  (250, 16,  60),
  ( 60,  5,  80),

# Software(80)
#   uses(2)          Storage Device(200)
#   send data to(4)  Business Service(1)
#   runs on(-5)      Computer Server(30)
#   runs on(-5)      Workstation(60)
#   managed by(22)   Technician(17)
#   depends on(1)    Software(80)
#   used by(-2)      IT Service(3)
  (80,  2, 200),
  (80,  1,  80),

# Application(81)
#   sends data to(4) IT Service(3)

# Network(250)
#   supported by(-10) Support Group(18)
#   managed by(22)    Technician(17)
#   includes(16)      Workstation(60)
#   includes(16)      Computer Server(30)
#   includes(16)      Router(253)
#   includes(16)      Switch(254)
  (250, 16, 253),
  (250, 16, 254),

# Router(253)
#   used by(-1)      Computer Server(30)
#   connected to(6)  Switch Port(255)
#   connected to(6)  Firewall(251)
#   supported by(10) Support Group(18)
#   backed up by(21) Router(253)
#   member of(-16)   Network(250)
#   managed by(22)   Technician(17)
  (253,  6, 255),
  (253,  6, 251),
  (253, 21, 253),

# Switch(254)
#   includes(16)     Switch Port(255)
#   supported by(10) Support Group(18)
#   backed up by(21) Switch(254)
#   member of(-16)   Network(250)
#   managed by(22)   Technician(17)
  (254, 16, 255),
  (254, 21, 254),

# Switch Port(255)
#   connected to(6)  Firewall(251)
#   connected to(6)  Router(253)
#   connected to(6)  Switch(254)
#   member of(-16)   Switch(254)
  (255,  6, 251),

# Firewall(251)
#   connected to(6) Access Point(261)
#   connected to(6) Switch Port(255)
#   connected to(6) Router(253)
  (251,  6, 261),

# Access Point(161)
#   connected to(6) Computer Server(30)
#   connected to(6) Firewall(251)
#   connected to(6) Workstation(60)


# Cluster(210)
#   contains(18)     Computer Server(30)
#   connected to(6)  Storage Device(200)
  (210,  6, 200),

# Storage Device(200)
#   used by Software
#   exchange data with(20) Workstation(60)
#   exchange data with(20) Computer Server(30)
#   managed by(22)         Technician(17)
#   connected to by(6)     Cluster(210)

# Rack(233)
#   located in Data Center(230)
#   contains Computer Server(30)

# Printer(281)
#   connected to(6) Workstation(60)

# Data Center(230)
#   houses(-19) Rack(233)
#   houses Computer Server(30)
  (233, 19, 230);


--  //@UNDO
-- SQL to undo the change goes here.


TRUNCATE TABLE  ci_type_relations;