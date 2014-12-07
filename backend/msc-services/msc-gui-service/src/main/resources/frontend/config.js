/**
 * This file/module contains all configuration for the build process.
 */
module.exports = {
  system: {
    "app":     "Itsnow",
    "name":    "msc",
    "title":   "Msc",
    "label":   "运营商",
    "version": "${project.version}",
    "index":   "msc-index-${project.version}",
    "login":   "msc-login-${project.version}",
    "skin" :   "skin-1",
    "defaultUser" : "admin",
    "defaultPassword" : "secret",
    "builtAt" : "${build.timestamp}"
  },
  /**
   * The `build_dir` folder is where our projects are compiled during
   * development and the `compile_dir` folder is where our app resides once it's
   * completely built.
   */
  build_dir: '../../build',
  deploy_dir: '../../deploy',

  /**
   * This is a collection of file patterns that refer to our app code (the
   * stuff in `src/`). These file paths are used in the configuration of
   * build tasks. `js` is all project javascript, less tests. `ctpl` contains
   * our reusable components' (`src/common`) template HTML files, while
   * `atpl` contains the same, but for our app's code. `html` is just our
   * main HTML file, `less` is our main stylesheet, and `unit` contains our
   * app's unit tests.
   */
  index_files: {
    js: [ 'lib/**/*.js', 'index/**/*.js', 'msc/index/**/*.js', '!**/*.spec.js' ],
    jade: [ 'lib/**/*.jade', 'index/**/*.jade', 'msc/index/**/*.jade', '!**/*.tpl.jade' ],
    js_unit: [ 'lib/**/*.spec.js', 'index/**/*.spec.js', 'msc/index/**/*.spec.js'],

    coffee: [ 'lib/**/*.coffee', 'index/**/*.coffee', 'msc/index/**/*.coffee', '!**/*.spec.coffee' ],
    coffee_unit: [ 'lib/**/*.spec.coffee', 'index/**/*.spec.coffee', 'msc/index/**/*.spec.coffee'],

    lib_tpl: [ '/lib/**/*.tpl.html', '/lib/**/*.tpl.jade' ],
    index_tpl: [ 'index/**/*.tpl.html', 'index/**/*.tpl.jade' ],
    ms_tpl: [ 'msc/index/**/*.tpl.html', 'msc/index/**/*.tpl.jade', 'msc/index/**/*.jade' ],
    general_tpl: [], // general_tpl is used in msp and msu, but here must identify an empty variable to run grunt successful

    less: 'less/index.less'
  },

  login_files: {
    js: [ 'lib/**/*.js', 'login/**/*.js', 'msc/login/**/*.js', '!**/*.spec.js'],
    jade: [ 'lib/**/*.jade', 'login/**/*.jade', 'msc/login/**/*.jade', '!**/*.tpl.jade'],
    js_unit: [ 'lib/**/*.spec.js', 'login/**/*.spec.js', 'msc/login/**/*.spec.js'],

    coffee: [ 'lib/**/*.coffee', 'login/**/*.coffee', 'msc/login/**/*.coffee', '!**/*.spec.coffee' ],
    coffee_unit: [ 'lib/**/*.spec.coffee', 'login/**/*.spec.coffee', 'msc/login/**/*.spec.coffee'],

    lib_tpl: [ 'lib/**/*.tpl.html', 'lib/**/*.tpl.jade' ],
    login_tpl: [ 'login/**/*.tpl.html', 'login/**/*.tpl.jade' ],
    ms_tpl: [ 'msc/login/**/*.tpl.html', 'msc/login/**/*.tpl.jade' ],
    general_tpl: [], // general_tpl is used in msp and msu, but here must identify an empty variable to run grunt successful

    less: 'less/login.less'
  },

  /**
   * This is a collection of files used during testing only.
   */
  test_files: {
    js: [
      '../vendor/angular-mocks/angular-mocks.js'
    ]
  },

  /**
   * This is the same as `app_files`, except it contains patterns that
   * reference vendor code (`vendor/`) that we need to place into the build
   * process somewhere. While the `app_files` property ensures all
   * standardized files are collected for compilation, it is the user's job
   * to ensure non-standardized (i.e. vendor-related) files are handled
   * appropriately in `vendor_files.js`.
   *
   * The `vendor_files.js` property holds files to be automatically
   * concatenated and minified with our project source files.
   *
   * The `vendor_files.css` property holds any CSS files to be automatically
   * included in our app.
   *
   * The `vendor_files.assets` property holds any assets to be copied along
   * with our app's assets. This structure is flattened, so it is not
   * recommended that you use wildcards.
   */
  vendor_files: {
    js: [
      '../vendor/jquery/dist/jquery.js',
      '../vendor/angular/angular.js',
      '../vendor/angular-resource/angular-resource.js',
      '../vendor/angular-route/angular-route.js',
      '../vendor/angular-animate/angular-animate.js',
      '../vendor/angular-sanitize/angular-sanitize.js',
      '../vendor/bootstrap/dist/js/bootstrap.js',
      '../vendor/angular-bootstrap/ui-bootstrap-tpls.js',
//    '../vendor/placeholders/angular-placeholders-0.0.1-SNAPSHOT.min.js',
      '../vendor/angular-ui-router/release/angular-ui-router.js',
      '../vendor/angular-ui-utils/modules/route/route.js',
      '../vendor/fuelux/dist/js/fuelux.js',
      '../vendor/ace-bootstrap/js/ace.js',
      '../vendor/ace-bootstrap/js/ace-elements.js',
      '../vendor/ace-bootstrap/js/ace-extra.js',
      '../vendor/typehead.js/bs2/typeahead-bs2.js',
      '../vendor/ng-table/ng-table.js',
      '../vendor/angular-ui-tree/dist/angular-ui-tree.js',
      '../vendor/angular-dialog-service/dialogs.js',
      '../vendor/angular-dialog-service/dialogs-default-translations.js',
      '../vendor/angular-translate/angular-translate.js',
      '../vendor/angular-auto-validate/dist/jcs-auto-validate.js',
      '../vendor/select2/select2.js',
      '../vendor/isteven-angular-multiselect/angular-multi-select.js'//,
      // Add angular mock in runtime for SLA demo temporally
      //'../vendor/angular-mocks/angular-mocks.js'
    ],
    css: [
//    '../vendor/angular/angular-csp.css',
      '../vendor/bootstrap/dist/css/bootstrap-theme.css',
      '../vendor/ace-bootstrap/css/*.css',
      '../vendor/angular-dialog-service/dialogs.css',
      '../vendor/angular-ui-tree/dist/angular-ui-tree.min.css',
      '../vendor/ng-table/ng-table.css',
      '../vendor/select2/select2.css',
      '../vendor/isteven-angular-multiselect/angular-multi-select.css'
//    '../vendor/fuelux/dist/css/fuelux.css'
    ],
    built_css: [
      '<%= build_dir %>/assets/bootstrap-theme.css',
      '<%= build_dir %>/assets/ace*.css',
      '<%= build_dir %>/assets/ng-grid.css',
      '<%= build_dir %>/assets/dialogs.css',
      '<%= build_dir %>/assets/angular-ui-tree.min.css',
      '<%= build_dir %>/assets/ng-table.css',
      '<%= build_dir %>/assets/select2.css',
      '<%= build_dir %>/assets/angular-multi-select.css'
    ],

    assets: [
      '../vendor/font-awesome/fonts/*.*',
      '../vendor/bootstrap/dist/fonts/*.*',
      '../vendor/bootstrap/dist/css/bootstrap-theme.css.map',
      '../vendor/ace-bootstrap/fonts/font1.woff',
      '../vendor/ace-bootstrap/fonts/font2.woff',
      '../vendor/ace-bootstrap/images/*.*',
      '../vendor/ace-bootstrap/avatars/*.*',
      '../vendor/ace-bootstrap/images/gallery/*.jpg',
      '../vendor/select2/*.png',
      '../vendor/select2/*.gif'
//    '../vendor/fuelux/dist/fonts/fuelux.*'
    ]
  }
};