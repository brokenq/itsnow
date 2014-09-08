/**
 * A new grunt file
 *
 * try to define a shared build policy, such as:
 *   build(config.xx.js)
 * and build the frontend app by several stages
 *
 * such as MSC Login App:
 *   build(config.vendor.js)
 *   build(config.lib.js)
 *   build(config.login.js)
 *   build(config.msc.login.js)
 * and MSC Index App:
 *   build(config.vendor.js)
 *   build(config.lib.js)
 *   build(config.index.js)
 *   build(config.msc.index.js)
 * and reduce two group into:
 *   build(config.vendor.js)
 *   build(config.lib.js)
 *   build(config.login.js)
 *   build(config.msc.login.js)
 *   build(config.index.js)
 *   build(config.msc.index.js)
 *
 * compile is only works on concrete app
 *   compile(config.msc.login.js)
 *   compile(config.msc.index.js)
 *
 * @author Jay Xiong
 * @since 2014/09/05
 */
module.exports = function ( grunt ) {

  /**
   * Load required Grunt tasks. These are installed based on the versions listed
   * in `package.json` when you do `npm install` in this directory.
   */
  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-coffee');
  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-jade');
  grunt.loadNpmTasks('grunt-conventional-changelog');
  grunt.loadNpmTasks('grunt-bump');
  grunt.loadNpmTasks('grunt-coffeelint');
  grunt.loadNpmTasks('grunt-karma');
  grunt.loadNpmTasks('grunt-ngmin');
  grunt.loadNpmTasks('grunt-html2js');

  var system = grunt.file.readJSON('.target');
  /**
   * Load in our build configuration file.
   */
  var userConfig = require('./' + system.name + '.config.js');

  /**
   * The default task is to build and compile.
   */
  grunt.registerTask( 'default', [ 'clean', 'build', 'compile' ] );

  /**
   * 处理一个模块/组件的典型工作
   */
  grunt.registerTask( 'component', [
    'jade',       // 将src目录中的jade文件转换为build目录中html文件(包括*.tpl.jade)
    'html2js',    // 将src与build目录中的(*.tpl.html)转换为 templates/xxx.js
    'jshint',     // 检查src目录中所有js文件语法(build目录中生成的js不需要检查)
    'coffeelint', // 检查src目录中所有的coffee文件语法
    'coffee',     // 将src目录下的coffee文件编译成build目录下的js文件
    'less',       // 将src目录下的less文件编译成build目录下的css文件
    'copy',       // 将src目录下的js文件复制到build目录下
    'delete'      // 将build目录下多余的文件(*.jade)删除
  ]);
  /**
   * 处理一个最终应用的典型工作
   */
  grunt.registerTask( 'app', [
    //将所有依赖的模块全部处理一遍(包括自身)
    'concat:css',      // 将css合并到同一个文件中
    'template',        // 处理主html/jade文件，设置其中的styles, scripts变量
    'jade',            // 处理主html文件
    'karmaconfig',     // 配置单元测试
    'karma:continuous' // 执行单元测试
  ]);

  grunt.registerTask( 'build', [

  ]);
};