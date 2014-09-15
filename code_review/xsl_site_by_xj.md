问题列表:

= 1. 控制器层

1.1 index方法的第一句日志没有keyword上下文信息
1.2 index方法的注释没有参数信息
1.3 index方法的第二句日志page信息不够readable

2.1 show/update/destroy等方法中对已经find出来的site的检查重复，应该放到 initSite里面
2.2 这些错误信息还表述错误(no -> sn)，另外出现这些错误，未必是用户没输入sn，而异常信息错误指导
2.3 update方法中日志 Updateing 拼写有误

3. 将service层异常均封装为web client exception, 应该try/catch时注明底层可能出现的错误的种类

= 2. 服务层

1.1 SiteService 类注释未写
1.2 SiteService 各个接口方法未描述

2 Site Manager未加 @Service 标记，无法工作

3.1 create方法检查site是否为null(有无必要？大家讨论)
3.2 create方法的事务问题(需要大家注意)
3.3 create之后直接返回的site，而site depart对象的关系变化未能反映出来

4 update方法中对departments的处理，竟然是再次全部创建一遍!

5 destroy 方法不建议返回对象 

6 create/update/destroy等方法，只有开始(ing)的日志输出，没有结束(ed)

= 3. Repository层

1.1 List<Site> find(...) 方法的参数，都是PageRequest里面属性，应该归并成一个 pageRequest
1.2 该方法应该被命名为 findAll

= 4. 模型层

1 site的workTime是不是应该 @NotNull ?
2 Site - Department 之间的关联关系对象不需要建模(同样的, SiteDeptRepository对象也不需要, 如果真的要，也不需要id`) 

= 5. script层
1 create_sites 脚本内容未对齐
2 creaet_sites 脚本中的外键引用，建议放在主键声明之后
3 create_sites 外键约束没有加(被注释掉,但应该加上)
4 work time# started_at, ended_at 两个字段的命名有问题，请联系上下文，看看怎么拼写?

= 6. 测试
1 许多地方assert一个number是否为null，验证无意义
2 SiteTest没有 optimize imports
3 SiteTest的Json验证不足，应该用Assert.assertEquals
4 SiteManagerTest#118, 字符串判断 ==
5 为了让测试通过，取消主外键, 并创建一些实际并未存储到db的对象，这种方法是错误的(Site->WorkTime, Site->ProcessDictionary)

= 7. 其他
1 Exception 包名大写!!! 严重问题
