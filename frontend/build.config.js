/**
 * This file/module contains all configuration for the build process.
 */
module.exports = {
  /**
   * The `build_dir` folder is where our projects are compiled during
   * development and the `compile_dir` folder is where our app resides once it's
   * completely built.
   */
  build_dir: 'build',
  compile_dir: 'deploy',

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
    js: [ 'src/**/*.js', '!src/login/**/*.js', '!src/**/*.spec.js', '!src/assets/**/*.js' ],
    jsunit: [ 'src/**/*.spec.js','!src/login/**/*.spec.js' ],
    
    coffee: [ 'src/**/*.coffee', '!src/login/**/*.coffee', '!src/**/*.spec.coffee' ],
    coffeeunit: [ 'src/**/*.spec.coffee', '!src/login/**/*.spec.coffee' ],

    atpl: [ 'src/app/**/*.tpl.html' ],
    ctpl: [ 'src/common/**/*.tpl.html' ],

    html: [ 'src/index.html' ],
    less: 'src/less/main.less'
  },

  login_files: {
    js: [ 'src/**/*.js', '!src/app/**/*.js', '!src/**/*.spec.js', '!src/assets/**/*.js' ],
    jsunit: [ 'src/**/*.spec.js','!src/app/**/*.spec.js' ],

    coffee: [ 'src/**/*.coffee', '!src/app/**/*.coffee', '!src/**/*.spec.coffee' ],
    coffeeunit: [ 'src/**/*.spec.coffee', '!src/app/**/*.spec.coffee' ],

    atpl: [ 'src/login/**/*.tpl.html' ],
    ctpl: [ 'src/common/**/*.tpl.html' ],

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
      'vendor/jquery/dist/jquery.min.js',
      'vendor/angular/angular.min.js',
      'vendor/bootstrap/dist/js/bootstrap.min.js',
      'vendor/angular-bootstrap/ui-bootstrap-tpls.min.js',
      'vendor/placeholders/angular-placeholders-0.0.1-SNAPSHOT.min.js',
      'vendor/angular-ui-router/release/angular-ui-router.js',
      'vendor/angular-ui-utils/modules/route/route.js',
      'vendor/ace-bootstrap/js/ace.js',
      'vendor/ace-bootstrap/js/ace-elements.js',
      'vendor/ace-bootstrap/js/ace-extra.js',
      'vendor/typehead.js/bs2/typeahead-bs2.min.js'
    ],
    css: [
        'vendor/angular/angular-csp.css',
        'vendor/bootstrap/dist/css/bootstrap-theme.min.css',
        'vendor/ace-bootstrap/css/*.css'
    ],
    assets: [
        'vendor/font-awesome/fonts/*.*',
        'vendor/bootstrap/dist/fonts/*.*',
        'vendor/ace-bootstrap/fonts/font1.woff',
        'vendor/ace-bootstrap/fonts/font2.woff',
        'vendor/ace-bootstrap/images/*.png',
        'vendor/ace-bootstrap/avatars/*.*',
        'vendor/ace-bootstrap/images/gallery/*.jpg'
    ]
  }
};
