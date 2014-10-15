<center>ITSNOW部署模块设计</center>
================================


1. 模型设计
----------

```
               +----------------+             +-------------+
               | DeployResource |-----------> | ConfigItem  |
               +--------^-------+  <extends>  +-------------+
                        |
                        |                 +----------+ 
                        |<extends>   /--->| Account  |
                        |           /     +----------+
                        |          /<belongs to>
        +--------------------+----/--------------+
        |                    |   /               |
   +------------+    +---------------+     +-------------+     +--------------+
   | ItsnowHost |    | ItsnowProcess |     | ItsnowRedis |     | ItsnowSchema |
   +--^--^--^---+    +---------------+     +--^----------+     +--^-----------+ 
      |  |  |      <run on>|  |  |<uses>      |     |             |        |
      |  |  +--------------+  |  +------------+     |             |<uses>  |  
      |  |                    +---------------------+-------------+        |
      |  +------------------------------------------+<store on redis host> |
      +--------------------------------------------------------------------+
                      <store on db host>
```

1. Itsnow Host, Process, Schema均从DeployResource继承，DeployResource是一个ConfigItem
2. Deploy Resource主要是支持设置配置属性(configuration:Properties )
3. Itsnow Process 属于某个账户，根据帐户的类型，应该部署相应的MSU或者MSP
4. Itsnow Process 运行于 Itsnow Host之中
5. Itsnow Process 需要使用一个Itsnow Schema作为其关系数据库存储
6. Itsnow Schema 也需要存储在一个Itsnow Host之中
7. 但，我们并没有约束Itsnow Process的主机与Itsnow Schema的主机是同一台主机

```
备注：
  Itsnow Process其实还对Redis有依赖，这个依赖关系与其对ItsnowSchema的依赖一样
  这里设计了一个 ItsnowRedis的Deploy Resource，但实际代码中暂时没有
```

2. 服务实现
----------

```
                              +-------------------------------------+
                              |                                     |
                              V                                     |<event>
                     SystemInvocationListener                       |
                              |                           +----------------------+
                  +------------------------+         /--->|  SystemInvokeService |
                  | ItsnowResourceManager  |        /     +----------------------+
                  |  |-invokeService ------+--------
                  |  |-invokeTranslator ---+--------\     +----------------------------+
                  +------------^-----------+         \--->| SystemInvocationTranslator |
                               |                          +----------------------------+ 
                               |<extends>                                                          
                               |
        +---------------------------+--------------------------+--------------------+
        |                           |                          |                    |
   +-------------------+  +----------------------+  +---------------------+   +--------------------+
   | ItsnowHostManager |  | ItsnowProcessManager |  | ItsnowSchemaManager |   | ItsnowRedisManager |
   +---------^---------+  +----------------------+  +---^-----------------+   +--------------------+
             |<use>           |        |<use>           |
             +----------------+        +----------------+

```

1. Itsnow Host, Process, Schema Manager均从ItnsowResourceManager继承
2. Itsnow Resource Manager引用了 SystemInvocationTranslator，用于为各个服务翻译步骤执行脚本为(SystemInvocation)
3. Itsnow Resource Manager引用了 SystemInvokeService，于执行各种SystemInvocation
4. Itsnow Resource Manager还实现了SystemInvocationListener，监听了SystemInvokeService在执行SystemInvocation过程中产生的事件（具体事件处理逻辑，由各个子类实现）
5. Itsnow Host, Process, Schema Manager在实现自身业务时，会调用相应的 translator, invoke 服务
6. Itsnow Process Manager会使用 Itsnow Host Service（用于查询Host），以及Itsnow Schema Service（即时创建相关Schema）

3. System Invoke Service实现原理

[参考这里](../system-invoke-service/README.md)
