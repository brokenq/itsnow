/**
 * This file/module contains all configuration for the build process.
 */
module.exports = {
  /**
   * The `build_dir` folder is where our projects are compiled during
   * development and the `compile_dir` folder is where our app resides once it's
   * completely built.
   */
  build_dir: 'msc/build',
  compile_dir: 'msc/deploy',

  /**
   * This is a collection of file patterns that refer to our app code (the
   * stuff in `src/`). These file paths are used in the configuration of
   * build tasks. `js` is all project javascript, less tests. `ctpl` contains
   * our reusable components' (`src/common`) template HTML files, while
   * `atpl` contains the same, but for our app's code. `html` is just our
   * main HTML file, `less` is our main stylesheet, and `unit` contains our
   * app's unit tests.
   */
  app_files: {
    js: [ 'src/common/**/*.js', 'src/app/**/*.js', 'src/msc/app/**/*.js', '!src/**/*.spec.js' ],
    jsunit: [ 'src/common/**/*.spec.js', 'src/app/**/*.spec.js', 'src/msc/app/**/*.spec.js'],

    coffee: [ 'src/common/**/*.coffee', 'src/app/**/*.coffee', 'src/msc/app/**/*.coffee', '!src/**/*.spec.coffee' ],
    coffeeunit: [ 'src/common/**/*.spec.coffee', 'src/app/**/*.spec.coffee', 'src/msc/app/**/*.spec.coffee'],

    ctpl: [ 'src/common/**/*.tpl.html' ],
    atpl: [ 'src/app/**/*.tpl.html' ],
    mtpl: [ 'src/msc/app/**/*.tpl.html' ],

    html: [ 'src/index.html' ],
    less: 'src/less/main.less'
  },

  login_files: {
    js: [ 'src/common/**/*.js', 'src/login/**/*.js', 'src/msc/login/**/*.js', '!src/**/*.spec.js'],
    jsunit: [ 'src/common/**/*.spec.js', 'src/login/**/*.spec.js', 'src/msc/login/**/*.spec.js'],

    coffee: [ 'src/common/**/*.coffee', 'src/login/**/*.coffee', 'src/msc/login/**/*.coffee', '!src/**/*.spec.coffee' ],
    coffeeunit: [ 'src/common/**/*.spec.coffee', 'src/login/**/*.spec.coffee', 'src/msc/login/**/*.spec.coffee'],

    ctpl: [ 'src/common/**/*.tpl.html' ],
    atpl: [ 'src/login/**/*.tpl.html' ],
    mtpl: [ 'src/msc/login/**/*.tpl.html' ],

    html: [ 'src/login.html' ],
    less: 'src/less/login.less'
  },

  /**
   * This is a collection of files used during testing only.
   */
  test_files: {
    js: [
      'vendor/angular-mocks/angular-mocks.js'
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
      'vendor/jquery/dist/jquery.js',
      'vendor/angular/angular.js',
      'vendor/angular-resource/angular-resource.js',
      'vendor/angular-route/angular-route.js',
      'vendor/angular-animate/angular-animate.js',
      'vendor/angular-sanitize/angular-sanitize.js',
      'vendor/bootstrap/dist/js/bootstrap.js',
      'vendor/angular-bootstrap/ui-bootstrap-tpls.js',
//      'vendor/placeholders/angular-placeholders-0.0.1-SNAPSHOT.min.js',
      'vendor/angular-ui-router/release/angular-ui-router.js',
      'vendor/angular-ui-utils/modules/route/route.js',
      'vendor/ace-bootstrap/js/ace.js',
      'vendor/ace-bootstrap/js/ace-elements.js',
      'vendor/ace-bootstrap/js/ace-extra.js',
      'vendor/typehead.js/bs2/typeahead-bs2.js',
      'vendor/ng-grid/ng-grid-2.0.11.debug.js',
      'vendor/angular-ui-tree/dist/angular-ui-tree.js',
      'vendor/angular-dialog-service/dialogs.js',
      'vendor/angular-dialog-service/dialogs-default-translations.js',
      'vendor/angular-translate/angular-translate.js'
    ],
    css: [
//      'vendor/angular/angular-csp.css',
      'vendor/bootstrap/dist/css/bootstrap-theme.css',
      'vendor/ace-bootstrap/css/*.css',
      'vendor/ng-grid/ng-grid.css',
      'vendor/angular-dialog-service/dialogs.css',
      'vendor/angular-ui-tree/dist/angular-ui-tree.min.css'
    ],
    assets: [
      'vendor/font-awesome/fonts/*.*',
      'vendor/bootstrap/dist/fonts/*.*',
      'vendor/bootstrap/dist/css/bootstrap-theme.css.map',
      'vendor/ace-bootstrap/fonts/font1.woff',
      'vendor/ace-bootstrap/fonts/font2.woff',
      'vendor/ace-bootstrap/images/*.png',
      'vendor/ace-bootstrap/avatars/*.*',
      'vendor/ace-bootstrap/images/gallery/*.jpg'
    ]
  }
};
