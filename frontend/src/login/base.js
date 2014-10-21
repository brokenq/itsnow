/**
 * The shared login Application(SPA)
 */
angular.module("Itsnow.Login", [
    'ngResource',
    'ngLocale',
    'ui.router',

    'Lib.Interceptor',
    'Lib.Templates',
    'Lib.Directives',
    'Login.Templates',
    'Itsnow.Security',
    'Login.Authenticate',
    'Login.Forgot'
])

  .config(function($stateProvider, $urlRouterProvider){
    $urlRouterProvider.otherwise("/authenticate");
  })

  .controller('LoginCtrl', ['$rootScope', '$scope', function($rootScope, $scope){
    $scope.$on('$stateChangeSuccess', function(evt, toState){
        if ( angular.isDefined( toState.data.pageTitle ) ) {
            $rootScope.pageTitle = toState.data.pageTitle + ' | ItsNow' ;
        }
    });
  }]);

