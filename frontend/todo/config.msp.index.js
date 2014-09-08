// MSP Index App的build配置
module.exports = {
  coffee: ['src/msp/app/**/*.coffee'],
  coffee_unit: ['src/msp/app/**/*.spec.coffee'],

  js: ['src/msp/app/**/*.js'],
  js_unit: ['src/msp/app/**/*.spec.js'],

  html_template: [ 'src/msp/app/**/*.tpl.html' ],
  jade_template: [ 'src/msp/app/**/*.tpl.jade' ]
};