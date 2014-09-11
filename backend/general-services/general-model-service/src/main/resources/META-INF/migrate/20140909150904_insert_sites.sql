-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.

INSERT INTO sites (id, sn, name, address, description, work_time_id, process_dictionary_id) VALUES
(1, '001', '大众一厂', '上海市安亭洛浦路63号', '测试', 1, 1),
(2, '002', '大众二厂', '安亭昌吉路82号',       '测试', 1, 1),
(3, '003', '大众三厂', '安亭曹安路5288号',     '测试', 1, 1),
(4, '004', '宁波工厂', '宁波市杭州湾新区',     '测试',  1, 1);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM sites;
