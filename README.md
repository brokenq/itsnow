Itsnow 新手指南
=================

1. 管理工具熟悉
-----------
##1.1 项目管理工具（http://track.happyonroad.net）

*请熊杰开通帐号
*熟悉项目之前的情况
*学习如何制定每个sprint的计划

##1.2 在线协作工具(http://www.processon.com)

*注册帐号，加入到itsnow小组中
*了解相关的架构图和一些原型图

2. 系统环境熟悉
-----------
##2.1 GIT学习

*请熊杰开通git帐号（http://git.happyonroad.net）
*下载git工具，并且安装
*设置ssh key(http://git.happyonroad.net/profile/keys)
*克隆项目代码至本地 git clone git@happyonroad.net:insight/itsnow.git itsnow
*查看分支 git branch -a
*切换至最新分支 git checkout Dev-0.2.0
*熟悉git常用命令，包括commit，add，checkout，pull，push，status，diff等

##2.2 Maven学习

*下载maven，安装并且设置环境变量
*修改settings.xml文件指向nexus.dnt.com.cn然后copy该文件至C:\Users\[jacky]\.m2\settings.xml
*打开命令行，转到itsnow代码路径
*通过命令行编译打包项目
*打包MSC系统，mvn -Pdnt,with-itsnow-msc package
*打包MSP系统，mvn -Pdnt,with-itsnow-general,with-itsnow-msp package
*打包MSU系统，mvn -Pdnt,with-itsnow-general,with-itsnow-msu package
*期间会报 Itsnow-Frontend模块Failure，可以通过下述的node.js模块修复
*打包结束后，会在/opt/releases/itsnow目录下生成各个系统的部署文件

##2.3 Node.js学习

*下载并且安装Node.js
*打开命令行，转到itsnow/frontend/目录
*npm -g install grunt-cli karma bower
*npm install
*编译Frontend模块
*mvn -Pdnt,with-itsnow-msc package
*mvn -Pdnt,with-itsnow-msp package
*mvn -Pdnt,tith-itsnow-msu package
 
##2.4 IntelliJ IDEA熟悉

*安装IDEA
*导入项目
*熟悉使用
*通过IDEA编译打包项目
*熟悉基本的快捷键

##2.5 MySQL熟悉

*安装MySQL 5.6以上版本
*安装MySQL客户端工具
*创建数据库schema itsnow-msc,itsnow-msp-001,itsnow-msu-001
*创建用户 itsnow/secret
*打开命令行，转到/opt/releases/itsnow/msc-0.2.0-SNAPSHOT/db目录
*执行 bin/migrate.cmd up系统会自动创建表和初始化数据
*通过客户端工具登录到MySQL中，了解基本表结构以及基本语法

##2.6 Redis熟悉

*安装Redis至本地
*启动Redis Server
*通过客户端连接至Redis
*学习基本的语句

##2.7 本地运行

*启动MySQL
*启动Redis
*转到/opt/release/itsnow/msc-0.2.0-SNAPSHOT/bin
*执行start.bat
*打开浏览器http://localhost:8071/login.html
*使用admin/secret登录，了解基本的菜单功能
*MSC,MSU,MSP系统的默认端口分别为8071，8072，8073

3. 文档学习
-----------
##3.1 版本发布文档
http://git.happyonroad.net/insight/itsnow/blob/master/release-notes.md
##3.2 平台模块开发指南
http://git.happyonroad.net/insight/itsnow/blob/master/backend/README.md
##3.3 平台模块测试开发指南
http://git.happyonroad.net/insight/itsnow/blob/master/backend/Test.md
##3.4 前端开发指南
http://git.happyonroad.net/insight/itsnow/blob/master/frontend/README.md

4. 自我练习
-----------
##4.1 前端模块开发练习

##4.2 后端模块开发练习

