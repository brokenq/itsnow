module.exports = function ( karma ) {
  karma.set({
    /** 
     * From where to look for files, starting with the location of this file.
     */
    basePath: '.',

    /**
     * This is the list of file patterns to load into the browser during testing.
     */
    files: [
      'vendor/jquery/dist/jquery.js',
        'vendor/angular/angular.js',
        'vendor/angular-resource/angular-resource.js',
        'vendor/angular-route/angular-route.js',
        'vendor/angular-animate/angular-animate.js',
        'vendor/angular-sanitize/angular-sanitize.js',
        'vendor/bootstrap/dist/js/bootstrap.js',
        'vendor/angular-bootstrap/ui-bootstrap-tpls.js',
        'vendor/angular-ui-router/release/angular-ui-router.js',
        'vendor/fuelux/dist/js/fuelux.js',
        'vendor/ace-bootstrap/js/ace.js',
        'vendor/ace-bootstrap/js/ace-elements.js',
        'vendor/ace-bootstrap/js/ace-extra.js',
        'vendor/ng-table/ng-table.js',
        'vendor/ng-grid/ng-grid-2.0.11.debug.js',
        'vendor/angular-ui-tree/dist/angular-ui-tree.js',
        'vendor/angular-dialog-service/dialogs.js',
        'vendor/angular-dialog-service/dialogs-default-translations.js',
        'vendor/angular-translate/angular-translate.js',
        'lib/interceptor.js',
        'app/base.js',
        'app/base.spec.js',
        'app/dialog/itsnow-dialog.js',
        'app/menu/menu.js',
        'app/menu/menu.spec.js',
        'app/table/itsnow-table.js',
        'msc/app/account/list.js',
        'msc/app/contract/contract.js',
        'msc/app/contract/contract.spec.js',
        'msc/app/index.js',
        'msc/app/index.spec.js',
        'msc/app/sla/sla.js',
        'msc/app/sla/sla.spec.js',
        'msc/app/user/user.js',
        'msc/app/user/user.spec.js',
        'lib/interceptor.js',
        'login/authenticate/authenticate.js',
        'login/authenticate/authenticate.spec.js',
        'login/base.js',
        'login/base.spec.js',
        'login/forgot/forgot.js',
        'login/forgot/forgot.spec.js',
        'login/security.js',
        'msc/login/login.js',
        'msc/login/login.spec.js',
        'msc/login/signup/signup.js',
        'msc/login/signup/signup.spec.js',
        'app/base.spec.js',
        'app/menu/menu.spec.js',
        'msc/app/contract/contract.spec.js',
        'msc/app/index.spec.js',
        'msc/app/sla/sla.spec.js',
        'msc/app/user/user.spec.js',
        'login/authenticate/authenticate.spec.js',
        'login/base.spec.js',
        'login/forgot/forgot.spec.js',
        'msc/login/login.spec.js',
        'msc/login/signup/signup.spec.js',
        'not/exist/placeholder'
    ],
    exclude: [
      'assets/**/*.js'
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
