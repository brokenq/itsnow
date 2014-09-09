/**
 * The shared login Application(SPA)
 */
angular.module("Itsnow.Login", [
    'ngResource',
    'ngLocale',
    'ui.router',

    'Lib.Interceptor',
    'Lib.Templates',
    'Login.Templates',
    'Itsnow.Security',
    'Login.Authenticate',
    'Login.Forgot'
])

  .config(function($stateProvider, $urlRouterProvider){
    $urlRouterProvider.otherwise("/authenticate");
  })

  .controller('LoginCtrl', function($scope){
    $scope.$on('$stateChangeSuccess', function(toState){
        if ( angular.isDefined( toState.data.pageTitle ) ) {
            $scope.pageTitle = toState.data.pageTitle + ' | ItsNow' ;
        }
    });
  });

