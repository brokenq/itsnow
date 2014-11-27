SET @WORK_TIMES_ID = (SELECT id from work_times where sn = 'plan1');

INSERT INTO sites (sn, name, address, description, work_time_id, area) VALUES
('001', '大众一厂', '上海市安亭洛浦路63号 ', '测试',  @WORK_TIMES_ID, '1'),
('002', '大众二厂', '安亭昌吉路82号 ',       '测试',  @WORK_TIMES_ID, '1'),
('003', '大众三厂', '安亭曹安路5288号 ',     '测试',  @WORK_TIMES_ID, '1'),
('004', '宁波工厂', '宁波市杭州湾新区 ',     '测试',  @WORK_TIMES_ID, '1');
