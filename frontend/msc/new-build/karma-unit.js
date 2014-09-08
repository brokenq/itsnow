module.exports = function ( karma ) {
  karma.set({
    /** 
     * From where to look for files, starting with the location of this file.
     */
    basePath: '../../',

    /**
     * This is the list of file patterns to load into the browser during testing.
     */
    files: [
      'msc/build/vendor/jquery/dist/jquery.js',
        'msc/build/vendor/angular/angular.js',
        'msc/build/vendor/angular-resource/angular-resource.js',
        'msc/build/vendor/angular-route/angular-route.js',
        'msc/build/vendor/angular-animate/angular-animate.js',
        'msc/build/vendor/angular-sanitize/angular-sanitize.js',
        'msc/build/vendor/bootstrap/dist/js/bootstrap.js',
        'msc/build/vendor/angular-bootstrap/ui-bootstrap-tpls.js',
        'msc/build/vendor/angular-ui-router/release/angular-ui-router.js',
        'msc/build/vendor/fuelux/dist/js/fuelux.js',
        'msc/build/vendor/ace-bootstrap/js/ace.js',
        'msc/build/vendor/ace-bootstrap/js/ace-elements.js',
        'msc/build/vendor/ace-bootstrap/js/ace-extra.js',
        'msc/build/vendor/ng-table/ng-table.js',
        'msc/build/vendor/ng-grid/ng-grid-2.0.11.debug.js',
        'msc/build/vendor/angular-ui-tree/dist/angular-ui-tree.js',
        'msc/build/vendor/angular-dialog-service/dialogs.js',
        'msc/build/vendor/angular-dialog-service/dialogs-default-translations.js',
        'msc/build/vendor/angular-translate/angular-translate.js',
        'msc/build/src/common/interceptor.js',
        'msc/build/src/app/base.js',
        'msc/build/src/app/dialog/itsnow-dialog.js',
        'msc/build/src/app/menu/menu.js',
        'msc/build/src/app/table/itsnow-table.js',
        'msc/build/src/msc/app/contract/contract.js',
        'msc/build/src/msc/app/index.js',
        'msc/build/src/msc/app/sla/sla.js',
        'msc/build/src/msc/app/user/user.js',
        'msc/build/src/login/authenticate/authenticate.js',
        'msc/build/src/login/base.js',
        'msc/build/src/login/forgot/forgot.js',
        'msc/build/src/login/security.js',
        'msc/build/src/msc/login/login.js',
        'msc/build/src/msc/login/signup/signup.js',
        
      'msc/build/templates-*.js',
      'vendor/angular-mocks/angular-mocks.js',
      'src/app/base.spec.js',
      'src/app/menu/menu.spec.js',
      'src/msc/app/contract/contract.spec.js',
      'src/msc/app/index.spec.js',
      'src/msc/app/sla/sla.spec.js',
      'src/msc/app/user/user.spec.js',
      'src/login/authenticate/authenticate.spec.js',
      'src/login/base.spec.js',
      'src/login/forgot/forgot.spec.js',
      'src/msc/login/login.spec.js',
      'src/msc/login/signup/signup.spec.js',
      'no/exist/placeholder'
    ],
    exclude: [
      'msc/build/src/assets/**/*.js'
    ],
    frameworks: [ 'jasmine' ],
    plugins: [ 'karma-jasmine', 'karma-chrome-launcher', 'karma-coffee-preprocessor' ],
    preprocessors: {
      '**/*.coffee': 'coffee'
    },

    /**
     * How to report, by default.
     */
    reporters: 'dots',

    /**
     * On which port should the browser connect, on which port is the test runner
     * operating, and what is the URL path for the browser to use.
     */
    port: 9018,
    runnerPort: 9100,
    urlRoot: '/',

    /** 
     * Disable file watching by default.
     */
    autoWatch: false,

    /**
     * The list of browsers to launch to test on. This includes only "Firefox" by
     * default, but other browser names include:
     * Chrome, ChromeCanary, Firefox, Opera, Safari, PhantomJS
     *
     * Note that you can also use the executable name of the browser, like "chromium"
     * or "firefox", but that these vary based on your operating system.
     *
     * You may also leave this blank and manually navigate your browser to
     * http://localhost:9018/ when you're running tests. The window/tab can be left
     * open and the tests will automatically occur there during the build. This has
     * the aesthetic advantage of not launching a browser every time you save.
     */
    browsers: [
      'Chrome'
    ]
  });
};

