# Itsnow 前端开发指南

基于 [AngularJS](http://angularjs.org) 的WEB前端项目.

***

## 1.快速使用

本项目基于 git上开源的 [ng-boilerplate](https://github.com/joshdmiller/ng-boilerplate) 进行二次定制

在进行前端开发之前，开发者需要先安装好 nodejs，保证nodejs的bin目录在当前path下，而后执行：

```sh
$ npm -g install grunt-cli karma bower
$ npm install
$ bower install
```

本项目包括Itsnow的MSC/MSU/MSP三个子系统的前端部分，可以分别build，有两种方式进行build：

### 方式A： 直接使用命令行进行build

在build之前，开发者需要先为grunt build设定`.target`内容，如为MSC build前端：

```JSON
{
    "name":    "msc",
    "title":   "Msc",
    "version": "0.1.6",
    "app":     "msc-app-0.1.6",
    "login":   "msc-login-0.1.6"
}
```
```sh
$ grunt build|compile|default --verbose
```
build出来的结果存储在 {msc|msu|msp}/{build|deploy}目录中

### 方式B： 使用MAVEN进行build，build的任务信息已经在pom.xml中定义

```sh
$mvn package -Pdnt,with-itsnow-msc
$mvn package -Pdnt,with-itsnow-msp
$mvn package -Pdnt,with-itsnow-msu
```

build出来的结果不仅会被存储在当前目录下的 {msc|msu|msp}/{build|deploy}中，还同时被copy到itsnow相应的release路径下

## 2. 项目结构

```
itsnow/frontend/
  |- grunt-tasks/
  |- karma/
  |- vendor/
  |  |- <各种第三方代码，所有子系统的所有应用都会引入它们>
  |  |- angular-bootstrap/
  |  |- bootstrap/
  |  |- placeholders/
  |- src/
  |  |- common/
  |  |  |- <在 main和login两类基础代码之间复用的代码>
  |  |- app/
  |  |  |- <main app的基础代码>
  |  |- login/
  |  |  |- <login app的基础代码>
  |  |- msc/
  |  |  |- app/
  |  |    |- <msc主应用的最终代码>
  |  |  |- login/
  |  |    |- <msc登录应用的最终代码>
  |  |- msp/
  |  |  |- app/
  |  |    |- <msp主应用的最终代码>
  |  |  |- login/
  |  |    |- <msp登录应用的最终代码>
  |  |- msu/
  |  |  |- app/
  |  |    |- <msu主应用的最终代码>
  |  |  |- login/
  |  |    |- <msu登录应用的最终代码>
  |  |- assets/
  |  |  |- <各种静态文件>
  |  |- less/
  |  |  |- login.less
  |  |  |- main.less
  |- .bowerrc
  |- bower.json
  |- build.config.js
  |- Gruntfile.js
  |- module.prefix
  |- module.suffix
  |- package.json
```

- `karma/`  - 前端单元测试模板文件.
- `src/`    - 前端程序代码. [Read more &raquo;](src/README.md)
- `vendor/` - 第三方库. [Bower](http://bower.io)，在执行 `bower install`时，所有第三方库都会被安装到本目录
  如果你需要增加第三方库，那么应该先在`bower.json`里面声明，而后再次执行 `bower install`
  而不是直接向其中添加
- `.bowerrc` - Bower的配置文件. 主要作用就是告知bower，配置文件名称为 `bower.json`，需要安装到vendor目录
- `bower.json` - 里面声明了项目名称(MSC|P|U统一的)，版本号，以及最重要的对第三方库的依赖关系.
- `{msc|msu|msp}.config.js` - 三个前端子系统的各自配置文件
- `Gruntfile.js` - 统一的Grunt脚本，类似于maven的pom.xml，ant的ant.xml，Rails项目的Rakefile
- `module.prefix` and `module.suffix` - 合并JS代码时的辅助脚本，能够将应用代码包装到一个延迟加载的函数中
- `package.json` - 统一的项目信息
- `changelog.tpl`, `CHANGELOG.MD`: 发布说明
- `.target`： 用于指导grunt到底build哪个前端子系统的控制文件
- `{msc|msp|msu}/build`: build之后的输出目录，主要用于开发调试，其中的js未合并，css虽然合并了，但未压缩
- `{msc|msp|msu}/deploy`: compile之后的输出目录，主要用于最终发布，其中的js已经合并且压缩，css合并且压缩


## 3.开发规范

首先，明确本文中提到的几个词汇的具体含义：

1.  `子系统(subsystem)`： msc, msp, msu 等前端程序
2.  `应用(app)`:   login, main 等前端程序的两大组成部分
3.  `login app`：未登录用户所看到的前端应用内容
4.  `main app`： 已登录用户所看到的前端应用内容(__TODO app目录改名为main更好__)

### 3.1 目录作用规范

1. 第三方库会被所有子系统中所有应用加载
2. `src/common`下的内容也会被所有子系统所有应用加载，与vendor的差别在于这里是我们开发的内容(__TODO common改名为lib更好__)
3. `src/app`下的内容会被所有子系统的main app所加载
4. `src/login`下的内容会被所有子系统下的login app所加载
5. `src/{msc|msu|msp}/login` 为最终应用，在这里进行相应登录应用的最终组装
6. `src/{msc|msu|msp}/app` 为最终应用，在这里进行相应主应用的最终组装

### 3.2 JS编码规范

#### 1. 模块命名

1. 顶层模块一般命名为 `Itsnow.$ModuleName`，如 `Itsnow.App`,`Itsnow.MscApp`
2. 子模块一般命名为: `$ParentModule.$ChildModule`，为了避免模块名到处充斥了 `Itsnow`， 非顶级模块可以去除 `Itsnow`，如 `Itsnow.MscApp` 的`SLA`子模块可以命名为 `MscApp.SLA`；

#### 2. 模块编写

