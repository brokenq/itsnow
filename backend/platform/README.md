# Itsnow后端平台说明

## 1. 前言 Preface

**Itnsow backend platform** 基于 [spring component framework](http://git.happyonroad.net/happyonroad/component-framework/blob/master/sustain/README-cn.md) 开发，平台主要提供了部分基础特性：

1. Spring MVC 容器
2. Mybatis ORM 机制（包括migrate机制）
3. Spring Security 机制
4. 其他基本支撑/约束环境

并针对应用开发者提供了额外的扩展机制和编程约束

1. Mybatis Feature Resolver
2. Spring MVC Feature Resolver

详细的开发说明，请参考 [模块开发指南](http://git.happyonroad.net/insight/itsnow/blob/Dev-0.1.3/backend/README.md)

## 2. Itsnow业务模块说明

首先， Itsnow系统后端的采用MySQL做关系数据库存储，Redis做内存数据以及消息，以下规划(replicatio)的原则适用于mysql和redis

数据库schema设计为  1(msc) + n(msu) + m(msp) 
在每个数据库schema中均会存在 msc schema, 仅有部分的msu 或 msp的schema。
在某个数据库instance上具有有哪些msu , msp schema由运营任意根据硬件条件和运营需求决定。


```
              +-----------------------------+ 
              |instance-01                  |
              |   mysql.itsnow_msc(master)--|----
              |   redis.db0(master)         |-| |
              +-----------------------------+ | |
                                              | |
              +-----------------------------+ | |
              |instance-02                  | | |
              |   mysql.itsnow_msc(slave) <-|-+ |
              |   mysql.itsnow_msu_001      | | |       
              |   mysql.itsnow_msp_001      | | | 复制(replicate)
              |   redis.db0(slave)        <-|-| |
              |   redis.db1(msu_001)        |   |
              |   redis.db2(msu_002)        |   |
              +-----------------------------+   |
                                                |
              +-----------------------------+   |
              |instance-03                  |   |
              |   mysql.itsnow_msc(slave) <-|---+
              |   mysql.itsnow_msu_002      |   |
              |   mysql.itsnow_msp_002      |   |
              |   mysql.itsnow_msu_003      |   |
              |   mysql.itsnow_msp_004      |   |
              |   redis.db0(slave)        <-|---|
              |   redis.db1(msu_002)        |   
              |   redis.db2(msp_002)        |   
              |   redis.db3(msu_003)        |   
              |   redis.db4(msp_004)        |   
              +-----------------------------+

```


 
一般会存在三种部署单元，各自均只能连接到一个数据库（关系/内存）实例上，访问 msc + 自身schema：

```
                                      +-----------------------+
    +-----+                           |instance-01            |
    | msc |---------------------------|   itsnow_msc(master)--|----
    +-----+                           +-----------------------+ | |
                                                                | |
                                      +-----------------------+ | |
    +---------+                       |instance-02            | | |
    | msu_001 |-----------------------|   itsnow_msc(slave) <-|-+ |
    +---------+                      /|   itsnow_msu_001      |   |
                                    / |   itsnow_msp_001      |   | 复制(replicate)
    +---------+                    /  +-----------------------+   |
    | msp_001 |-------------------/                               |
    +---------+                       +-----------------------+   |
                                      |instance-03            |   |
                                  /---|   itsnow_msc(slave) <-|---+
    +---------+                  /    |   itsnow_msu_002      |
    | msu_002 |-----------------/  ---|   itsnow_msp_002      |
    +---------+                   /   |   itsnow_msu_003      |
                                 /  --|   itsnow_msp_004      |
    +---------+                 /  /  +-----------------------+
    | msu_003 |----------------/  /
    +---------+                  /
                                /
    +---------+                /
    | msp_004 |---------------/ 
    +---------+   
```

其中， 仅有 msc 部署单元对 instance-01的itsnow_msc schema具有写权限，其他部署单元对itsnow_msc的slave均只有只读权限，这样可以保证我们只需要从instance-01向其他instance单向复制。
同理，redis.db0也仅有master进行写操作

我们在项目组织上，将他们分解为如下模块构成：

| 模块               | 说明         |   建议  |
| ------------------|--------------|------------| 
| release           | 任意部署单元的部署模板项目 | - |
| platform          | 提供平台特性，被 release 项目所依赖 | - |
| common services   | 提供任意部署单元都会用到的通用服务 |关于 itsnow_msc的直接读服务, 关于每个部署单元的主schema均有的模型的管理服务（如 groups, acls, 工作流） |
| general services  | 提供 msu,msp 部署单元公用的一般服务 | 关于 msu, msp的主schema中均有的模型的管理服务，指向msc的远程服务 |
| msc services      | msc部署单元才用到的服务 | 面向 itsnow_msc 的管理服务，并以SPI的形式暴露给 general, msc, msp services 使用 |
| msu services      | msu部署单元才用到的服务 | msu 独有的业务模型的管理服务 |
| msp services      | msp部署单元才用到的服务 | msp 独有的业务模型的管理服务 |



## 3. 平台基本结构

首先，由于项目进度问题(2014/08/04)，平台还处于快速开发，尚未稳定的阶段，其不稳定主要表现在：

1. 平台的内容可能有所增加（在spring mvc, mybatis 之外，可能会随项目开发深入增加新的平台特性，相应业务模块也可以增加新的约束）
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

平台启动后会扫描 $release/repository 目录， 加载其中artifact id不以api结尾的jar包，一般这些jar包（我们称其为业务模块/service package）都是面向某个数据库业务表，并实现相应的从WEB Controller到业务bean，以及数据库操作的一系列功能。

为了支撑业务模块的端到端开发，平台(通过Service Package Manager)支持用户设置一个Manifest/Default-Config指令

```
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifestEntries>
                            <Default-Config>S,A,D,W</Default-Config>
                        </manifestEntries>
                    </archive>
                </configuration>
            </plugin>
```  

Default Config 中的值，S = Service, A = App, D = DB, W = Web，也可以写作: `<Default-Config>true</Default-Config>`
等价于如下配置：

```xml
<configuration>
  <archive>
    <manifestEntries>
     <Service-Config>dnt.itsnow.platform.config.DefaultServiceConfig</Service-Config>
     <App-Config>dnt.itsnow.platform.config.DefaultAppConfig</App-Config>
     <DB-Repository>dnt.itsnow.repository</DB-Repository>
     <Web-Repository>dnt.itsnow.web.controller</Web-Repository>
    </manifestEntries>
  </archive>
</configuration>
```

1、 Default Service Config 内容

```java
    public void defineServices() {
        //数据库相关服务
        importService(DataSource.class);
        importService(PlatformTransactionManager.class, "*", "transactionManager");
        importService(SqlSessionFactory.class);
        importService(Configuration.class);
        //Spring MVC相关服务
    }

```

2、 Default App Config 内容

```java
@Configuration
@Import({dnt.spring.DefaultAppConfig.class})
@ComponentScan("dnt.itsnow.support")
```

一般业务模块的服务bean，应该将放到 dnt.itsnow.support 包下，这样，他们只要有了 `@Component`, `@Service` , `@Configuration` 等Spring标记，就会被默认加载

备注： 控制器代码不建议放到这个目录，而是放到 `dnt.itsnow.web.controller`下

----------

DB和Web两个扩展特性是平台在spring-component-frame的application/service之外额外扩展特性：

-----------

### 4.1. Mybatis扩展

#### 1. 数据库模型migrate

   开发者需要在jar包的/META-INF/migrate目录增加相应的migrate脚本
   migrate脚本需由开发者在 msc|msu|msp 任意部署实体中：
   
```
  cd /path/to/msc
  cd db
  # 如果没设置过权限
  chmod u+x bin/migrate
  bin/migrate new <the description>    
```
  新生成的migrate脚本文件应该从 /path/to/msc/db/migrate/scripts/xxxx_yyy.sql移动到实际项目resoures目录中，并在被相应模块打包到最终jar包中，部署到/path/to/msc/repository中
  系统部署后，工程师应该先将msc,msu,msp等部署单元的数据库schema，数据库账户，权限等准备就绪，具体步骤为：

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

mysql> create user 'itsnow_msp_001'@'localhost' identified by 'secret';
mysql> grant select,execute on itsnow_msp_001.* to 'itsnow_msp_001'@'localhost';
```  
而后返回命令行 

```
 # 保证 db/migrate/environments/development.properties 文件中数据库，用户名，密码设置正确
 
 # 先对msc进行migrate
 cd /path/to/msc/db
 bin/migrate up
 
 # 而后再对msu或msp进行
 cd /path/to/msu/db
 bin/migrate up
 
 cd /path/to/msp/db
 bin/migrate up
```

#### 2. 数据库模型映射 

平台默认会扫描每个service package的 `dnt/itsnow/repository` 目录，寻找其中Mybatis的Mapper类以及Mapper XML
具体mybatis mapper如何 撰写，请参考 [mybatis 文档](http://mybatis.github.io/mybatis-3/)

开发者也可以通过设置在相应模块的输出jar包的Manifest.mf文件的 **DB-Repository** 指令来修改默认的包路径，如果需要扫描多个包路径，请用逗号分割；

通过pom.xml进行配置的示例如下：

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifestEntries>
                            <DB-Repository>
                              dnt.itsnow.db.repository,dnt.itsnow.repository
                            </DB-Repository>
                        </manifestEntries>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

#### 3. Mybatis 配置

很多模块扩展时，除了增加Mapper(Repository)之外，还需要对mybatis的配置进行修改，最典型的就是为实体模型增加别名（以及注册新的type handlers）

此时，开发者只需要在其输出jar包中增加META-INF/mybatis.xml 平台会自动加载，如：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
        <typeAlias type="dnt.itsnow.model.User" alias="User"/>
    </typeAliases>
</configuration>
```

### 4.2. Spring Mvc扩展

在平台已经将service-package加载，并为其构建好相应的application context后，本扩展将会默认扫描 `dnt.itsnow.web.controller` 目录，寻找所有的 `@Controller`, `@RestController` 等Spring MVC标准标记（包括 `@Component, @Service, @Configuration` 等，但不建议放这里）

而这些控制器，可以 如一般的spring mvc controller一样， 通过`@Autowire`注入依赖的beans和服务，通过`@RequestMapping`处理特定路由。 

如果需要定义额外的Web控制器扫描路径：

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifestEntries>
                            <Web-Repository>
                              dnt.itsnow.web.conroller,dnt.itsnow.controller
                            </Web-Repository>
                        </manifestEntries>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>
```


## 5. Spring Security 整合

### 5.1 CRSF保护

通过`GET /security/csrf` 可以获取当前的CSRF信息，如下：

```json
{"headerName":"X-CSRF-TOKEN","parameterName":"_csrf","token":"541ac4a0-36d2-41eb-a073-962e163c3219"}
```

每次POST请求之后，CSRF Token都会变化，请在客户端及时`GET /security/csrf`更新。

### 5.2 用户认证

默认支持两种认证方式

1. Form认证   
   登录：`POST /api/session` 其中带上 username, password 等认证信息
   登出：`DELETE /api/session`  
   
2. Basic Authentication认证
   curl -u user:password http://host:port
   
认证成功之后，当前用户名可以根据Servlet规范，从 `request.getRemoteUser()` 获得， 当前用户身份可以从 `request.getUserPrincipal()` 获得   

### 5.3 用户授权

  请参考 [Spring Security](http://docs.spring.io/spring-security/site/docs/3.2.4.RELEASE/reference/htmlsingle/#authorization)