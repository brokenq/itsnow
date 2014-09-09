/**
 * This file/module contains all configuration for the build process.
 */
module.exports = {
  /**
   * The `build_dir` folder is where our projects are compiled during
   * development and the `compile_dir` folder is where our app resides once it's
   * completely built.
   */
  build_dir: '../msc/test-build',
  compile_dir: '../msc/deploy',

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
    jsunit: [ 'lib/**/*.spec.js', 'index/**/*.spec.js', 'msc/index/**/*.spec.js'],

    coffee: [ 'lib/**/*.coffee', 'index/**/*.coffee', 'msc/index/**/*.coffee', '!**/*.spec.coffee' ],
    coffeeunit: [ 'lib/**/*.spec.coffee', 'index/**/*.spec.coffee', 'msc/index/**/*.spec.coffee'],

    ctpl: [ 'lib/**/*.tpl.html' ],
    atpl: [ 'index/**/*.tpl.html' ],
    mtpl: [ 'msc/index/**/*.tpl.html' ],

    less: 'less/index.less'
  },

  login_files: {
    js: [ 'lib/**/*.js', 'login/**/*.js', 'msc/login/**/*.js', '!**/*.spec.js'],
    jsunit: [ 'lib/**/*.spec.js', 'login/**/*.spec.js', 'msc/login/**/*.spec.js'],

    coffee: [ 'lib/**/*.coffee', 'login/**/*.coffee', 'msc/login/**/*.coffee', '!**/*.spec.coffee' ],
    coffeeunit: [ 'lib/**/*.spec.coffee', 'login/**/*.spec.coffee', 'msc/login/**/*.spec.coffee'],

    ctpl: [ 'lib/**/*.tpl.html' ],
    atpl: [ 'login/**/*.tpl.html' ],
    mtpl: [ 'msc/login/**/*.tpl.html' ],

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
      '../vendor/ng-grid/ng-grid-2.0.11.debug.js',
      '../vendor/angular-ui-tree/dist/angular-ui-tree.js',
      '../vendor/angular-dialog-service/dialogs.js',
      '../vendor/angular-dialog-service/dialogs-default-translations.js',
      '../vendor/angular-translate/angular-translate.js'
    ],
    css: [
//    '../vendor/angular/angular-csp.css',
      '../vendor/bootstrap/dist/css/bootstrap-theme.css',
      '../vendor/ace-bootstrap/css/*.css',
      '../vendor/ng-table/ng-table.css',
      '../vendor/ng-grid/ng-grid.css',
      '../vendor/angular-dialog-service/dialogs.css',
      '../vendor/angular-ui-tree/dist/angular-ui-tree.min.css'
//    '../vendor/fuelux/dist/css/fuelux.css'
    ],
    assets: [
      '../vendor/font-awesome/fonts/*.*',
      '../vendor/bootstrap/dist/fonts/*.*',
      '../vendor/bootstrap/dist/css/bootstrap-theme.css.map',
      '../vendor/ace-bootstrap/fonts/font1.woff',
      '../vendor/ace-bootstrap/fonts/font2.woff',
      '../vendor/ace-bootstrap/images/*.png',
      '../vendor/ace-bootstrap/avatars/*.*',
      '../vendor/ace-bootstrap/images/gallery/*.jpg'
//    '../vendor/fuelux/dist/fonts/fuelux.*'
    ]
  }
};
