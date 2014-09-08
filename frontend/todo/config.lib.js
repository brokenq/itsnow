// lib模块的build配置
module.exports = {
  coffee: ['src/common/**/*.coffee'],
  coffee_unit: ['src/common/**/*.spec.coffee'],

  js: ['src/common/**/*.js'],
  js_unit: ['src/common/**/*.spec.js'],

  html_template: [ 'src/common/**/*.tpl.html' ],
  jade_template: [ 'src/common/**/*.tpl.jade' ],

  assets: ['src/assets/*']
};