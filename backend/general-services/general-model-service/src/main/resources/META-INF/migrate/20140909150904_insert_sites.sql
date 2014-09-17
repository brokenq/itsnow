-- // insert_process_dictionaries
-- Migration SQL that makes the change goes here.

SET @WORK_TIMES_ID = (SELECT id from work_times where sn = 'plan1');
SET @PROCESS_DICTIONARY_ID = (SELECT id from process_dictionaries where code = 'inc001');

INSERT INTO sites (sn, name, address, description, work_time_id, process_dictionary_id) VALUES
('001', '大众一厂', '上海市安亭洛浦路63号 ', '测试',  @WORK_TIMES_ID, @PROCESS_DICTIONARY_ID),
('002', '大众二厂', '安亭昌吉路82号 ',       '测试',  @WORK_TIMES_ID, @PROCESS_DICTIONARY_ID),
('003', '大众三厂', '安亭曹安路5288号 ',     '测试',  @WORK_TIMES_ID, @PROCESS_DICTIONARY_ID),
('004', '宁波工厂', '宁波市杭州湾新区 ',     '测试',  @WORK_TIMES_ID, @PROCESS_DICTIONARY_ID);

-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM sites;
