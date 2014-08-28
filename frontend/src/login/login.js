/**
 * The shared login Application(SPA)
 */
angular.module("ItsNow.Login", [
    'ngResource',
    'ui.router',

    'Common.Interceptor',
    'Common.Templates',
    'Login.Templates',
    'ItsNow.Security',
    'Login.Authenticate',
    'Login.Forgot',
    'Login.Signup'
])

  .config(function($stateProvider, $urlRouterProvider){
    $urlRouterProvider.otherwise("/authenticate");
})

  .controller('LoginCtrl', function($scope){
    $scope.$on('$stateChangeSuccess', function LoginCtrl(toState){
        if ( angular.isDefined( toState.data.pageTitle ) ) {
            $scope.pageTitle = toState.data.pageTitle + ' | ItsNow' ;
        }
    });
});

