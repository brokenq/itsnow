Itsnow 版本发布
=================

统一规定如下:

1. 交付物获取
-----------
应该从[CI](http://ci.itsnow.com/)系统上获取相应的交付物，如果需要登录，请以guest身份登录

2. 开发代码地址
-----------
git@happyonroad.net:insight/itsnow.git Dev-x.y.z 分支(branch)

3. 发布代码地址
-----------
git@happyonroad.net:insight/itsnow.git Rel-x.y.z 标签(tag)

4. 系统演示地址
-----------

1. [MSC http://msc.itsnow.com](http://msc.itsnow.com)
2. [MSP http://dnt.itsnow.com](http://dnt.itsnow.com)
3. [MSU http://vw.itsnow.com](http://vw.itsnow.com)

5. 版本发布方法
--------------

准备工作:

1. 所有开发者保证自己的代码在本机可以通过
  
  * 单元测试

```sh
  cd path/to/itsnow
  mvn -Pdnt,with-itsnow-msc clean package
  mvn -Pdnt,with-itsnow-general,with-itsnow-msp clean package
  mvn -Pdnt,with-itsnow-general,with-itsnow-msu clean package
```

  * 集成测试（以msc的集成测试为例，msp, msu 均需要单独参照执行, 另外msp，msu集成测试时需要启动msc）

```sh
  cd /opt/releases/itsnow/msc-x.y.z-SNAPSHOT
  chmod u+x db/migrate
  db/migrate up
  bin/itsnow-msc start
  cd path/to/itsnow/backend/integration-test
  mvn -Pdnt,with-itsnow-msc test
```
  
2. 提交相应代码到origin上相应开发分支 Dev-x.y.z
3. 集成负责人在CI的 [Itsnow SAAS Platform](http://ci.itsnow.com/viewType.html?buildTypeId=itsnow_Continuous_Build)持续集成通过之后，进行正式的版本发布工作

发布工作:

1. 切换到master分支: `cd path/to/itsnow && git co master`
2. 更新origin: `git pull origin`
3. 将Dev-x.y.z的所有修改合并到master分支: `git merge Dev-x.y.z`
4. 在本文档中，为当前发布的版本撰写Release说明
5. 将itsnow所有pom.xml的版本号从 `x.y.z-SNAPSHOT` 改为 `x.y.z`
6. 重新执行一次: `mvn package`

```sh
  mvn -Pdnt,with-itsnow-msc clean package
  mvn -Pdnt,with-itsnow-general,with-itsnow-msp clean package
  mvn -Pdnt,with-itsnow-general,with-itsnow-msu clean package
```
7. 将对master的修改提交到origin上（origin会执行单元测试以及集成测试）:`git push origin master`
8. 检查CI上各个子系统是否通过Sprint Build
  8.1. 如果检查不通过，则要求相应开发人员解决问题，提交到Dev-x.y.z，而后从步骤2开始重复
  8.2. 如果检查通过，继续执行以下步骤
9. 打标签: `git tag Rel-x.y.z`
10. 提交新标签: `git push --tags`
11. 开辟新版本: `git co -b Dev-x.y.z+1`
12. 修改新版本号: 替换所有版本号 x.y.z 为 x.y.z+1-SNAPSHOT
13. 提交新的开发分支: `git push -u origin Dev-x.y.z+1:Dev-x.y.z+1`
14. 修改Itsnow CI的Continuous Build [version参数](http://ci.itsnow.com/admin/editBuildParams.html?id=buildType:itsnow_Continuous_Build)

部署工作:

1. 链接主机：ssh root@msc.itsnow.com
  1.1 密码：itsnow@team
2. 进入msc目录：cd /opt/itsnow/msc
3. 查看链接指向的版本：ll
  例如：/opt/itsnow/msc-x.y.z-SNAPSHOT/bin执行以下指令后,会更新成/opt/itsnow/msc-x.y.z/bin
3. 进入msc的脚本目录：script/msc
4. 执行脚本命令，更新链接指向的版本：upgrade.sh x.y.z
5. 查看链接指向的版本是否更新完成：
  5.1. cd ../msc
  5.2. ll

收尾工作:

1. 发出Release Mail
2. 通知所有开发人员为下个版本切换开发分支

0.1.5 发布说明
=================

1. 新增特性
-----------

1. 完成典型工作流
2. 完成CMDB/0.1版本，数据库中增加相应的CIType, CIRelationType以及记录
3. 框架层支持service wrapper，支持以服务的形式运行系统

2. 问题修复
-----------

1. 暂无

3. 交付物地址
-----------
1. [msc-0.1.5](http://ci.itsnow.com/repository/download/Itsnow_Sprint_Build_MSC/3783:id/msc-0.1.5.zip)
2. [msp-0.1.5](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSP/3784:id/msp-0.1.5.zip)
3. [msu-0.1.5](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSU/3782:id/msu-0.1.5.zip)

0.1.6 发布说明
=================

1. 新增特性
-----------

1. Frontend 支持隔离的msc,msu,msp
2. 支持完全动态的菜单
3. 后端系统中Incident流程被分解到MSU/MSP独立的模块中

2. 问题修复
-----------

1. 部分前端代码整理/单元测试用例再次开放

3. 交付物地址
--------------

1. [msc-0.1.6](http://ci.itsnow.com/repository/download/Itsnow_Sprint_Build_MSC/3832:id/msc-0.1.6.zip)
2. [msp-0.1.6](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSP/3833:id/msp-0.1.6.zip)
3. [msu-0.1.6](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSU/3834:id/msu-0.1.6.zip)

4. 系统演示地址
-----------

暂未部署

0.1.7 发布说明
=================

1. 新增特性
-----------

1. Frontend 完成注册form以及account列表展示
2. Backend 完成签约流程
3. Backend 完成故障流程
4. Backend 完成部分系统配置功能

2. 问题修复
-----------


3. 交付物地址
--------------

1. [msc-0.1.7](http://ci.itsnow.com/repository/download/Itsnow_Sprint_Build_MSC/3832:id/msc-0.1.7.zip)
2. [msp-0.1.7](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSP/3833:id/msp-0.1.7.zip)
3. [msu-0.1.7](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSU/3834:id/msu-0.1.7.zip)

4. 系统演示地址
-----------

暂未部署

0.1.8 发布说明
=================

1. 新增特性
-----------

1. Frontend 服务目录管理
2. Frontend 故障流程
3. Frontend 地点管理
4. Frontend 部门管理
5. Frontend 流程字典
6. Frontend 工作时间管理
7. Backend 实现MSC中MSU/P注册流程
8. Backend 部署运行环境支持
9. Backend 实现MSC中MSU/P注册的脚本开发工作

2. 问题修复
-----------


3. 交付物地址
--------------

1. [msc-0.1.8](http://ci.itsnow.com/repository/download/Itsnow_Sprint_Build_MSC/3832:id/msc-0.1.8.zip)
2. [msp-0.1.8](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSP/3833:id/msp-0.1.8.zip)
3. [msu-0.1.8](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSU/3834:id/msu-0.1.8.zip)

4. 系统演示地址
-----------

暂未部署

0.1.9 发布说明
=================

1. 新增特性
-----------

1. Frontend 主机界面和进程服务界面
2. Frontend 系统配置列表
3. Frontend MSP与MSU故障流程页面
4. Frontend MSP与MSU签约流程页面
5. Frontend ActionService组件初步实现
6. Backend 部署运行环境支持
7. Backend 实现MSC中MSU/P注册的脚本开发工作
8. Backend MSP与MSU故障流程

2. 问题修复
-----------


3. 交付物地址
--------------

1. [msc-0.1.9](http://ci.itsnow.com/repository/download/Itsnow_Sprint_Build_MSC/3832:id/msc-0.1.9.zip)
2. [msp-0.1.9](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSP/3833:id/msp-0.1.9.zip)
3. [msu-0.1.9](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSU/3834:id/msu-0.1.9.zip)

4. 系统演示地址
-----------

暂未部署

0.2.0 发布说明
=================

1. 新增特性
-----------

1. Frontend 部门管理树形结构前台展示
2. Frontend ActionService实现
3. Backend 解决平台web respond随机故障的问题
4. Backend 在itsnow部署环境中再次测试与集成
5. Backend 部门管理树形结构功能开发
6. Backend Activiti数据库表同步操作
7. Backend BSM系统架构Draft

2. 问题修复
-----------


3. 交付物地址
--------------

1. [msc-0.2.0](http://ci.itsnow.com/repository/download/Itsnow_Sprint_Build_MSC/3832:id/msc-0.2.0.zip)
2. [msp-0.2.0](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSP/3833:id/msp-0.2.0.zip)
3. [msu-0.2.0](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSU/3834:id/msu-0.2.0.zip)

4. 系统演示地址
-----------

1. [msc-0.2.0](http://msc.itsnow.com/login.html)
0.2.1 发布说明
=================

1. 新增特性
-----------

1.  Frontend 新增主机资源产品化
2.  Frontend 查看主机产品化
3.  Frontend MSP/MSU列表界面产品化
4.  Frontend Schema列表展示
5.  Frontend 注册界面前端产特性品化,前后端的校验
6.  Backend 维护主机进程和服务状态
7.  Backend 用户会话功能
8.  Backend 一键批准并开通MSU/MSP
9.  Backend 手工新增和删除Schema
10. Backend 工作时间和数据字典增删改查
11. Backend 新增公共服务目录
12. Backend 批准/拒绝新注册的MSU/MSP

2. 问题修复
-----------


3. 交付物地址
--------------

1. [msc-0.2.1](http://ci.itsnow.com/repository/download/Itsnow_Sprint_Build_MSC/3832:id/msc-0.2.1.zip)
2. [msp-0.2.1](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSP/3833:id/msp-0.2.1.zip)
3. [msu-0.2.1](http://ci.itsnow.com/repository/download/Itsnow_SprintBuild_MSU/3834:id/msu-0.2.1.zip)

4. 系统演示地址
-----------

1. [msc-0.2.1](http://msc.itsnow.com/login.html)