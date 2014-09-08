// MSU Index App的build配置
module.exports = {
  coffee: ['src/msu/app/**/*.coffee'],
  coffee_unit: ['src/msu/app/**/*.spec.coffee'],

  js: ['src/msu/app/**/*.js'],
  js_unit: ['src/msu/app/**/*.spec.js'],

  html_template: [ 'src/msu/app/**/*.tpl.html' ],
  jade_template: [ 'src/msu/app/**/*.tpl.jade' ]
};