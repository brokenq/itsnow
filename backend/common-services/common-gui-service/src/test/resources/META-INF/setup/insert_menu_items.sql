
INSERT INTO menu_items(id, parent_id, name, type, url) VALUES
(1, null, '用户', '0', 'index.user'),
(2, null, 'SLA', '0', 'index.sla'),
(3, null, '合同', '0', 'index.contract'),
(4, null, '故障单', '0', ''),
(5, 4, 'MSC故障单', '0', 'index.list-trouble-ticket'),
(6, 4, 'MSP故障单', '0', 'index.msp-list-trouble-ticket'),
(7, 5, 'MSC故障单1', '0', 'index.list-trouble-ticket'),
(8, 5, 'MSC故障单2', '0', 'index.list-trouble-ticket'),
(9, 6, 'MSP故障单1', '0', 'index.msp-list-trouble-ticket'),
(10, 9, 'MSP故障单1-1', '0', 'index.msp-list-trouble-ticket');
