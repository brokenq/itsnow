# Itsnow后端平台说明

## 1. 前言 Preface

**Itnsow backend platform** 基于 [spring component framework](http://git.happyonroad.net/happyonroad/component-framework/blob/master/sustain/README-cn.md) 开发，在平台部分主要提供了部分基础特性：

1. Spring MVC 容器
2. Mybatis ORM 机制（包括migrate机制）
3. Spring Security 机制
4. 其他基本支撑/约束环境

并针对应用开发者提供了额外的扩展机制和编程约束

1. Mybatis Feature Resolver
2. Spring MVC Feature Resolver

## 2. Itsnow业务模块说明

首先， Itsnow系统后端的数据库schema设计为  1(msc) + n(msu) + m(msp) 
在每个数据库schema中均会存在 msc schema, 仅有部分的msu 或 msp的schema。
在某个数据库instance上具有有哪些msu , msp schema由运营任意根据硬件条件和运营需求决定。

```
              +-----------------------+ 
              |instance-01            |
              |   msc-schema(master)--|----
              +-----------------------+ | |
                                        | |
              +-----------------------+ | |
              |instance-02            | | |
              |   msc-schema(slave) <-|-+ |
              |   msu_001-schema      |   |       
              |   msp_001-schema      |   | 复制(replicate)
              +-----------------------+   |
                                          |
              +-----------------------+   |
              |instance-02            |   |
              |   msc-schema(slave) <-|---+
              |   msu_002-schema      |
              |   msp_002-schema      |
              |   msu_003-schema      |
              |   msp_004-schema      |
              +-----------------------+

```


 
一般会存在三种部署单元，各自均只能连接到一个数据库实例上，访问 msc + 自身schema：

```
                                      +-----------------------+
    +-----+                           |instance-01            |
    | msc |---------------------------|   msc-schema(master)--|----
    +-----+                           +-----------------------+ | |
                                                                | |
                                      +-----------------------+ | |
    +---------+                       |instance-02            | | |
    | msu_001 |-----------------------|   msc-schema(slave) <-|-+ |
    +---------+                      /|   msu_001-schema      |   |
                                    / |   msp_001-schema      |   | 复制(replicate)
    +---------+                    /  +-----------------------+   |
    | msp_001 |-------------------/                               |
    +---------+                       +-----------------------+   |
                                      |instance-02            |   |
                                  /---|   msc-schema(slave) <-|---+
    +---------+                  /    |   msu_002-schema      |
    | msu_002 |-----------------/  ---|   msp_002-schema      |
    +---------+                   /   |   msu_003-schema      |
                                 /  --|   msp_004-schema      |
    +---------+                 /  /  +-----------------------+
    | msu_003 |----------------/  /
    +---------+                  /
                                /
    +---------+                /
    | msp_004 |---------------/ 
    +---------+   
```

其中， 仅有 msc 部署单元对 instance-01的msc-schema具有写权限，其他部署单元对msc-schama的slave均只有只读权限，这样可以保证我们只需要从instance01向其他instance单向复制msc-schema。


我们在项目组织上，将他们分解为如下模块构成：

| 模块               | 说明         |   建议  |
| ---------------------------------|------------| 
| release           | 任意部署单元的部署模板项目 | - |
| platform          | 提供平台特性，被 release 项目所依赖 | - |
| common services   | 提供任意部署单元都会用到的通用服务 |关于 msc-schema的直接读服务, 关于每个部署单元的主schema均有的模型的管理服务（如 groups, acls, 工作流） |
| general services  | 提供 msu,msp 部署单元公用的一般服务 | 关于 msu, msp的主schema中均有的模型的管理服务，指向msc的远程服务 |
| msc services      | msc部署单元才用到的服务 | 面向 msc schema的管理服务，并以SPI的形式暴露给 general, msc, msp services 使用 |
| msu services      | msu部署单元才用到的服务 | msu 独有的业务模型的管理服务 |
| msp services      | msp部署单元才用到的服务 | msp 独有的业务模型的管理服务 |



## 3. 平台基本结构

首先，由于项目进度问题(2014/08/04)，平台还处于快速开发，尚未稳定的阶段，其不稳定主要表现在：

1. 平台的内容可能有所增加（在spring mvc, mybatis 之外，可能会随项目开发深入增加）
2. 平台的组织结构可能调整（即便是已有的特性，如mybatis，也可能被被抽取为单独的组件，成为平台的可选特性）
3. 平台本身也可能被从 itsnow 提取到 components中，作为bsm/dss 等其他子系统的的开发环境
4. 平台的标准范式和API等也可能需要继续深入开发，整理

平台首先是一个spring component framework 定义的组件，更确切的来说，是一个服务组件，其中包括一个 spring app context， 由如下 module (configuration | beans)构成

```
  +---------------+
  | config module |<------+
  +---------------+       |
                          | read configurations                        +-------------+    
  +-----------------+     |                                            | xxx-services|
  | database module |-----+                                           /+-------------+
  +-----------------+                    +-------------------------+ /
                                         | service package manager |/
  +-------------------+                  +-------------------------+\   +------------+
  | spring mvc module |-------------+                                \--|yyy-services|
  +-------------------+             |                                   +------------+
                                    | root web context 
  +------------------------+        |
  | spring security module |<-------+
  +------------------------+
  
```

参考 `PlatformConfiguration` 类的javadoc说明，平台的主要加载顺序为：

```
 * 加载的逻辑顺序为:
 * Spring Component AppLauncher
 *   |- Platform Configuration
 *   |   |- Config module
 *   |   |- Database module
 *   |   |- JettyServer
 *   |   |    |- AnnotationConfiguration
 *   |   |    |    |- SpringMvcLoader
 *   |   |    |    |    |- SpringSecurityConfig
 *   |   |    |    |    |   |- SpringMvcConfig
 *   |   |- ServicePackageManager
 *   |   |    | - All kinds of service app in repository folder
```

除了其自身组成提供的功能之外，平台还提供：

1. 基本实体/模型类(Record, Page, PageRequest)
2. 基本工具类(Rest Facade)
3. 基本配置类(DefaultAppConfig, Default ServiceConfig)
4. 基本Web控制机制
   A. Application Controller 作为一般控制器父类
   B. Before|After Filter作为一般控制器编程支撑
   C. 基本异常机制
   D. 一般性信息，如路由信息(routes), CSRF信息(security/csrf)

## 4. 平台扩展能力

平台启动后会扫描 release 目录， 加载其中artifact id 不以apid结尾的jar包，一般这些jar包（我们称其为业务模块/service package）都是面向某个数据库业务表，并实现相应的从WEB Controller到业务bean，以及数据库操作的一系列功能。

为了支撑业务模块的端到端开发，平台(通过Service Package Manager)支持以下两个扩展特性：

### 4.1. Mybatis扩展

1. 数据库模型migrate
   开发者需要在jar包的/META-INF/migrate目录增加相应的migrate脚本
   migrate脚本的生成方式，需由开发者在 msc|msu|msp 任意部署实体中：
   
```
  cd /path/to/msc
  cd db
  # 如果没设置过权限
  chmod u+x bin/migrate
  bin/migrate new <the description>    
```
  生成完毕之后，migrate脚本文件应该从 /path/to/msc/db/migrate/scripts/xxxx_yyy.sql移动到实际项目resoures目录中，并在被相应模块打包到最终jar包中，部署到/path/to/msc/repository中
  系统部署后，工程师应该先讲msc部署单元的数据库schema准备就绪，具体步骤为：

```
 # 登录到mysql控制台 
mysql -uroot -p 
mysql> create database itsnow_msc default character set utf8; 
mysql> create database itsnow_msu_001 default character set utf8;
mysql> create database itsnow_msp_001 default character set utf8;
mysql> create database <other schemas> default character set utf8;
mysql> create user 'itsnow_msc'@'localhost' identified by 'secret';
mysql> grant all privileges on itsnow_msc.* to 'itsnow_msc'@'localhost';

mysql> create user 'itsnow_msu_001'@'localhost' identified by 'secret';
mysql> grant select,execute on itsnow_msu_001.* to 'itsnow_msu_001'@'localhost';
```  

### 4.2. Spring Mvc扩展

## 5. 标准扩展模板
