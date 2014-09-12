Itsnow 版本发布
=================

统一规定如下:

1. 交付物获取
-----------
应该从[CI](http://ci.dnt.com.cn/)系统上获取相应的交付物，如果需要登录，请以guest身份登录

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
  mvn -Pdnt,with-itsnow-msc package
  mvn -Pdnt,with-itsnow-general,with-itsnow-msp package
  mvn -Pdnt,with-itsnow-general,with-itsnow-msu package
```

  * 集成测试（以msc的集成测试为例，msp, msu 均需要单独参照执行）

```sh
  cd /opt/releases/itsnow/msc-x.y.z-SNAPSHOT
  chmod u+x db/migrate
  db/migrate up
  bin/itsnow-msc start
  cd path/to/itsnow/backend/integration-test`
  mvn -Pdnt,with-itsnow-msc package
```
  
2. 提交相应代码到origin上相应开发分支 Dev-x.y.z
3. 集成负责人在CI的 [Itsnow SAAS Platform](http://ci.dnt.com.cn/viewType.html?buildTypeId=itsnow_Continuous_Build)持续集成通过之后，进行正式的版本发布工作

发布工作:

1. 切换到master分支: `cd path/to/itsnow && git co master`
2. 更新origin: `git pull origin`
3. 将Dev-x.y.z的所有修改合并到master分支: `git merge Dev-x.y.z`
4. 在本文档中，为当前发布的版本撰写Release说明
5. 将itsnow所有pom.xml的版本号从 `x.y.z-SNAPSHOT` 改为 `x.y.z`
6. 重新执行一次: `mvn package`

```sh
  mvn -Pdnt,with-itsnow-msc package
  mvn -Pdnt,with-itsnow-general,with-itsnow-msp package
  mvn -Pdnt,with-itsnow-general,with-itsnow-msu package
```
7. 将对master的修改提交到origin上（origin会执行单元测试以及集成测试）
8. 检查CI上各个子系统是否通过Sprint Build
  8.1. 如果检查不通过，则要求相应开发人员解决问题，提交到Dev-x.y.z，而后从步骤2开始重复
  8.2. 如果检查通过，继续执行以下步骤
9. 打标签: `git tag Rel-x.y.z`
10. 提交新标签: `git push --tags`
11. 开辟新版本: `git co -b Dev-x.y.z+1`
12. 提交新的开发分支: `git push -u origin Dev-x.y.z+1:Dev-x.y.z+1`
13. 修改Itsnow CI的Continuous Build [version参数](http://ci.dnt.com.cn/admin/editBuildParams.html?id=buildType:itsnow_Continuous_Build)

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
1. [msc-0.1.5](http://ci.dnt.com.cn/repository/download/Itsnow_Sprint_Build_MSC/3783:id/msc-0.1.5.zip)
2. [msp-0.1.5](http://ci.dnt.com.cn/repository/download/Itsnow_SprintBuild_MSP/3784:id/msp-0.1.5.zip)
3. [msu-0.1.5](http://ci.dnt.com.cn/repository/download/Itsnow_SprintBuild_MSU/3782:id/msu-0.1.5.zip)

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

1. [msc-0.1.6](http://ci.dnt.com.cn/repository/download/Itsnow_Sprint_Build_MSC/3832:id/msc-0.1.6.zip)
2. [msp-0.1.6](http://ci.dnt.com.cn/repository/download/Itsnow_SprintBuild_MSP/3833:id/msp-0.1.6.zip)
3. [msu-0.1.6](http://ci.dnt.com.cn/repository/download/Itsnow_SprintBuild_MSU/3834:id/msu-0.1.6.zip)

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

1. [msc-0.1.7](http://ci.dnt.com.cn/repository/download/Itsnow_Sprint_Build_MSC/3832:id/msc-0.1.7.zip)
2. [msp-0.1.7](http://ci.dnt.com.cn/repository/download/Itsnow_SprintBuild_MSP/3833:id/msp-0.1.7.zip)
3. [msu-0.1.7](http://ci.dnt.com.cn/repository/download/Itsnow_SprintBuild_MSU/3834:id/msu-0.1.7.zip)

4. 系统演示地址
-----------

暂未部署
