
-- // insert_dictionaries
-- Migration SQL that makes the change goes here.
DELETE FROM dictionaries WHERE code in ("inc001","inc002","inc004","inc005");
INSERT INTO dictionaries (code,name,label,description,detail) VALUES
    ('location','区域','location',NULL,'[{"key":"上海","value":"sh"},{"key":"北京","value":"bj"},{"key":"广州","value":"gz"},{"key":"深圳","value":"sz"},{"key":"杭州","value":"hz"},{"key":"南京","value":"nj"}]'),
    ('priority','优先级','priority',NULL,'[{"key":"高","value":"high"},{"key":"中","value":"middle"},{"key":"低","value":"low"}]'),
    ('urgency','紧急程度','urgency',NULL,'[{"key":"高","value":"high"},{"key":"中","value":"middle"},{"key":"低","value":"low"}]'),
    ('impact','影响程度','impact',NULL,'[{"key":"高","value":"high"},{"key":"中","value":"middle"},{"key":"低","value":"low"}]'),
    ('request_type','请求类型','request_type',NULL,'[{"key":"电话","value":"phone"},{"key":"网络","value":"network"},{"key":"短信","value":"sms"}]'),
    ('category','分类','category',NULL,'[{"key":"软件","value":"software"},{"key":"硬件","value":"hardware"},{"key":"网络","value":"network"},{"key":"数据库","value":"database"},{"key":"其他","value":"other"}]'),
    ('workflow','流程分类','workflow',NULL,'[{"key":"故障","value":"incident"},{"key":"问题","value":"problem"},{"key":"变更","value":"change"},{"key":"发布","value":"release"}]');

-- //@UNDO
-- SQL to undo the change goes here.