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

[以上]

3. 部署环境说明
---------------

### 3.1.1 基本环境

操作系统：CentOS 7
JVM: Jdk1.7
包管理系统： YUM

### 3.1.2 MSC环境说明

地址：srv1.itsnow.com

1. Redis 2.8.16, 
   * 配置文件路径: /etc/redis.conf
   * 数据文件路径: /opt/redis/msc.rdb
2. MySql 5.6.20
   * 配置文件路径: /usr/my.cnf
   * 数据文件路径: /var/lib/mysql
3. MSC系统
   * 程序路径: /opt/itsnow/msc
   * 备份数据: /opt/itsnow/backup
   * 本地代码: /opt/source ()仅用于在该机器进行build时使用)
   * 本地输出: /opt/releases/itsnow
   * 部署支持: /opt/system

### 3.1.3 MSU/MSP等部署机环境说明

地址: srv2.itsnow.com, srv3.itsnow.com 等

初始配置时，不包括任何redis/mysql安装程序
通过MSC系统开通该主机后，其应该有：

1. Redis 2.8.16, 
   * 配置文件路径: /etc/redis.conf
   * 数据文件路径: /opt/redis/msc.rdb
2. MySql 5.6.20
   * 配置文件路径: /usr/my.cnf
   * 数据文件路径: /var/lib/mysql

通过MSC系统部署MSU/MSP应用后, MSU/MSP系统

   * MSU程序路径: /opt/itsnow/msu
   * MSP程序路径: /opt/itsnow/msp
   * 各个系统路径: /opt/itsnow/itsnow_$account_domain
   * 部署支持: /opt/system

可以参考[脚本设计](http://git.happyonroad.net/insight/itsnow/tree/Dev-0.2.0/backend/release/script)了解目录规划（但其中内容现在有些过期，需要更新）


4. 脚本说明
----------

  本章仅介绍各个脚本的作用，读者也可以阅读 [SystemInvocationTranslation](http://git.happyonroad.net/insight/itsnow/blob/Dev-0.2.0/backend/msc-services/msc-deploy-service/src/main/java/dnt/itsnow/support/SystemInvocationTranslation.java)了解这些脚本是如何被组织在一起工作的

  以下各个脚本一般并不做前置条件检测，如删除服务进程目录时，并不去做服务进程是否已经被停止的检测，这种检测和保障，归属为msc部署服务中java代码的责任，应该通过ItsnowProcess对象的状态来预先检测。
  
```
  这也说明了我们的脚本开发原则：脚本负责一个专注的事务，而该事务的判断和相关依赖，由外部解决
```

  所有的脚本均存放在 backend/release/script目录，基本上，所有的本地脚本存放在msc目录(本地还是远程，是相对于msc部署单元而言)

脚本路径                | 作用                | 备注
-----------------------|--------------------|---------
msc/trust_me.sh        | 让目标主机信任msc主机 | 主机开通第一步
msc/prepare_host.sh    | 把msc主机上的部署支持脚本，程序copy到目标主机 | 主机开通第二步
platform/provision.sh  | 开通目标主机，安装mysql,redis等 |主机开通第三步
msc/replicate.sh       | 让目标主机的数据库复制msc的itsnow_msc schema | 主机开通第三步
platform/prepare_ms.sh | 让目标主机使用特定msu/msp系统作为以后的部署单元代码基础 | 主机开通第四，五步
platform/delist.sh     | 停止并卸载目标主机的mysql,redis | 主机下线第一步
msc/delist_host.sh     | 删除目标主机上各种遗留信息 | 主机下线第二步
platform/create_db.sh  | 创建schema，数据库用户，授权  | create schema
platform/drop_db.sh    | 删除schema，数据库用户  | drop schema
platform/prepare_deploy.sh | 根据已有的msu/msp系统创建服务进程目录 | 部署服务进程第一步
platform/deploy.sh     | 将创建的服务进程各方面配置好   | 部署服务进程第二步
platform/migrate_ms.sh | 将创建的服务进程数据库migrate好 | 部署服务进程第三步
msc/proxy_ms.sh        | 让msc上的nginx代理相应的服务进程 | 部署服务第四步
msc/unproxy_ms.sh      | 让msc上的nginx取消代理相应的服务进程 | 取消部署服务第一步
platform/undeploy.sh   | 删除服务进程目录      | 取消部署第二步
platform/start_ms.sh   | 启动特定服务进程      | start process
platform/stop_ms.sh    | 停止特定服务进程      | stop process
... | ... | ...
msc/upgrade.sh | MSC系统升级脚本  | 当版本发布，或者系统升级后，采用该脚本对msc进行升级
platform/interplate.sh | 按照key=value模式执行文件字符串替换的脚本 | 配置服务进程时使用
platform/interplate2.sh | 按照#{value}模式执行文件字符串替换的脚本 | 配置服务进程时使用


5. System Invoke Service实现原理
-------------------------------
[参考这里](http://git.happyonroad.net/insight/itsnow/tree/Dev-0.2.0/backend/msc-services/system-invoke-service)
