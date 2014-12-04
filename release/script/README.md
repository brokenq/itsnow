脚本规划说明
==========

1. 目录结构
==========

```
release/script/
  |- platform/
  |  |- 平台脚本，也就是所有部署实体都会拥有的脚本
  |- msc/
  |  |- 仅MSC需要执行到的脚本
  |- msu/
  |  |- 仅MSU需要执行到的脚本
  |- msp/
  |  |- 仅MSP需要执行到的脚本
```
在系统build阶段，不同的部署实体，将会仅有 platform/**.sh + (msc|msu|msp)/**.sh 两个部分

2. 使用说明
==========

根据使用场景，这些脚本可以分为两大类：

1.  由运营人员登录到系统控制台手工直接执行的脚本，包括：
    * 系统升级脚本(MSC|MSU|MSP)
    * 系统备份脚本(备份脚本很多时候是由cron表驱动，但也理解为系统代替运营人员登录执行)
2.  由运营人员通过MSC系统管理界面执行业务操作而触发的脚本
    * 主机类管理脚本
      * 主机开通(配置)
      * 主机下线
    * 数据库Schema管理脚本
      * 创建数据库schema
      * 销毁数据库schema
    * 系统进程管理脚本
      * 创建部署实体
      * 启动部署实体
      * 检测部署实体状态
      * 停止部署实体
      * 销毁部署实体


3. 系统发布环境设计
====================

3.1 一般环境
----------

  1. 操作系统: CentOS 6/7
  2. JDK: 1.7.0 build >= 67
  3. MySQL: 5.6.20
  4. Redis: 2.8.16

备注:

```
  A.以上jdk,mysql,redis相关的可执行程序目录均应该配置在root用户的path环境变量中
  B.MySQL运行于3306端口，root用户密码存储于/root/.mysql_pwd文件内
  C.Redis运行于6379端口，配置文件存放在 /etc/redis.conf中
  D.MySQL与Redis均作为系统服务，自动启动

```

## 3.2 MSC发布目录设计 ##

```
/opt/itsnow/
  |- download/
  |  |- <该目录存放下载下来的原始发布程序zip包>
  |  |- msc-0.1.6.zip
  |  |- msc-0.1.7.zip
  |  |- ...
  |- backup/
  |  |- db/
  |  |   |- <该目录存放按天自动归档的数据库dump文件>
  |  |   |- 2014_09_17
  |  |   |- 2014_09_18
  |  |   |   |- itsnow_msc.gz
  |  |- config/
  |  |   |- <该目录存放按天自动归档的系统配置文件>
  |  |   |- 2014_09_17
  |  |   |- 2014_09_18
  |  |   |   |- msc_config.gz
  |  |- logs/
  |  |   |- 2014_09_17
  |  |   |- 2014_09_18
  |  |   |   |- msc_nginx_access.gz
  |  |   |   |- msc_jetty_access.gz
  |  |   |   |- msc_running.1.gz
  |  |   |   |- msc_running.2.gz
  |- msc <软链接指向当前实际使用的程序版本，如:msc-0.1.7>
  |- msc-0.1.6
  |- msc-0.1.7 <从download/目录下对应程序包解压缩后的程序版本>
  |  |- bin
  |  |- boot
  |  |- config
  |  |   |- logback.xml
  |  |   |- now.properties
  |  |   |- wrapper.conf
  |  |   |- nginx.conf    <linked by /etc/nginx/conf.d/msc.conf>
  |  |- db
  |  |- script
  |  |   |- platform
  |  |   |- msc
  |  |- lib
  |  |- logs
  |  |   |- nginx_access.log
  |  |   |- jetty_access.log
  |  |   |- Itsnow_msc.log
  |  |- repository
  |  |- webapp

```

由于msc系统不会存在多个实例，所以采用两层链接：

  `msc -> msc-x.y.z `

而且实际msc系统的运行配置/日志等信息，就存放在 msc-x.y.z里面


## 3.3 MSU/MSP 发布目录设计 ##

由于msu/msp系统存在多个实例，需要采用三层链接方式：

  `msu_abc -> msu -> msu-x.y.z`

```
  当系统升级时，运营人员只需要将msu的软链接指向一个新的版本，各个msu的实例就可以得到升级
  另外，我们约束，在一台主机中，不应该同时存在多个不同版本的msu实例
  假如未来真的有不同的msu运行于不同的版本，我们可以更改msu_abc的链接规则: `msu_abc -> msu-x.y.z`
```

```
/opt/itsnow/

  |- download/
  |  |- <该目录存放下载下来的原始发布程序zip包>
  |  |- msu-0.1.6.zip
  |  |- msu-0.1.7.zip
  |  |- msp-0.1.7.zip
  |  |- ...
  |- backup/
  |  |- db/
  |  |   |- <该目录存放按天自动归档的数据库dump文件>
  |  |   |- 2014_09_17
  |  |   |- 2014_09_18
  |  |   |   |- itsnow_msu_001.gz
  |  |   |   |- itsnow_msp_001.gz
  |  |- config/
  |  |   |- <该目录存放按天自动归档的系统配置文件>
  |  |   |- 2014_09_17
  |  |   |- 2014_09_18
  |  |   |   |- msp_001_config.gz
  |  |   |   |- msu_001_config.gz
  |  |- logs/
  |  |   |- 2014_09_17
  |  |   |- 2014_09_18
  |  |   |   |- msu_001_nginx_access.gz
  |  |   |   |- msu_001_jetty_access.gz
  |  |   |   |- msu_001_running.1.gz
  |  |   |   |- msu_001_running.2.gz
  |  |   |   |- msp_001_nginx_access.gz
  |  |   |   |- msp_001_jetty_access.gz
  |  |   |   |- msp_001_running.1.gz
  |  |   |   |- msp_001_running.2.gz
  |- msu <软链接指向当前实际使用的程序版本，如:msu-0.1.7>
  |- msu-0.1.6
  |- msu-0.1.7: 从download/目录下对应程序包解压缩后的程序版本
  |  |- bin
  |  |- boot
  |  |- config
  |  |- db
  |  |- script
  |  |   |- platform
  |  |   |- msu
  |  |- lib
  |  |- logs
  |  |- repository
  |  |- webapp
  |- msu_001
  |- msu_002
  |  |- bin       <link to msu/bin>
  |  |- boot      <link to msu/boot>
  |  |- config
  |  |   |- logback.xml      <copied/interpolate from msu/config/logback.xml>
  |  |   |- now.properties   <copied/interpolate from msu/config/now.properties>
  |  |   |- wrapper.conf     <copied/interpolate from msu/config/wrapper.conf>
  |  |   |- nginx.conf       <linked by /etc/nginx/conf.d/msu_002.conf>
  |  |- db
  |  |   |- bin   <link to msu/db/bin>
  |  |   |- lib   <link to msu/db/lib>
  |  |   |- migrate
  |  |   |   |- drivers <link to msu/db/migrate/drivers>
  |  |   |   |- scripts <link to msu/db/migrate/scripts>
  |  |   |   |- environments
  |  |   |   |  |- production.properties <copied/interpolate from db/migrate/environments/development.properties>
  |  |- script    <link to msu/script>
  |  |- lib       <link to msu/lib>
  |  |- logs
  |  |   |- nginx_access.log
  |  |   |- jetty_access.log
  |  |   |- Itsnow_msu_001.log
  |  |- repository<link to msu/repository>
  |  |- webapp    <link to msu/webapp>

```

4.脚本设计
=========

4.1 手工类脚本
------------

  待设计

4.2 触发类脚本
------------

1. 主机开通

```
  初始执行主机为msc所在机器
  ssh执行有两种方式:
    a. 机器之间已经建立互信(推荐)
    b. 采用用户名/密码方式
  目标主机的yum源已经配置妥当
```
  分为两个大的步骤：
  A. 在msc主机上执行 `provision.sh`
     * 通过ssh在目标主机上创建目录 /opt/system 目录
     * 从msc/script目录 copy 相关脚本过去
     * 从/opt/system/binary copy mysql, redis安装文件过去
  B. 在目标主机上执行: `provision.sh`
     * 安装开发类工具:
     * 安装redis
     * 启动redis
     * 安装mysql
     * 启动mysql


2. 主机下线

  待设计

3. 创建数据库schema(`create_db.sh`)
  a. 读取数据库配置
  b. 执行数据库脚本( create database, create user, grant privileges)

4. 删除数据库schema(`drop_db.sh`)
  a. 读取数据库配置
  b. 执行数据库脚本( drop database, drop user, revoke privileges)

5. 创建部署实体(`config_itsnow.sh`)

```
  适用于msu/msp，以msu_001为例
  输入信息可以参考数据库模型 itsnow_process
  当前主机为itsnow_process指定的主机，工作目录为: /opt/itsnow/
```

  a. mkdir itsnow_msu_001
  b. create links to msu
  c. generate itsnow_msu_001/config/msu_001.properties
  d. copy and interpolate
     * msu/bin/start.sh
     * msu/bin/stop.sh
     * msu/bin/itsnow-msu.sh    -> msu_001/bin/itsnow-msu_001.sh
     * msu/config/logback.xml
     * msu/config/nginx.conf
     * msu/config/now.properties
     * msu/config/wrapper.properties
     * msu/db/migrate/environments/development.properties
  e. ln -s msu/config/nginx.conf /etc/nginx/conf.d/msu_001.conf
  f. create db
  g. migrate db
  h. kill -HUP nginx

6. 启动部署实体
  a. clean msu_001/logs/Itsnow-msu_001.log  (为了以后follow日志)
  b. `msu_001/bin/itsnow-msu_001 start `

7. 检测部署实体的状态
  `msu_001/bin/itsnow-msu_001 status`

8. 停止部署实体
  `msu_001/bin/itsnow-msu_001 stop`

9. 销毁部署实体
  待设计