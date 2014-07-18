/**
 * Created by kadvin on 14-7-2.
 */
var app = angular.module("ItsNow.Login", [
  'templates-login',
  'templates-common',
  'ItsNow.Login.Authenticate',
  'ItsNow.Login.Forgot',
  'ItsNow.Login.Signup',
  'ngResource',
  'ui.router'
]);


app.run(['$http', function($http) {
    $http.get('security/csrf').success(function(data) {
        console.log("CSRF Success: " + data);
        $http.defaults.headers.common[data.headerName] = data.token;
    }).error(function(data) {
        console.log("CSRF ERROR: " + data);
    });
}]);

app.config(function($stateProvider, $urlRouterProvider){
  $urlRouterProvider.otherwise("/authenticate");
});

app.controller('LoginCtrl', function($scope){
  $scope.$on('$stateChangeSuccess', function LoginCtrl(event, toState){
    if ( angular.isDefined( toState.data.pageTitle ) ) {
      $scope.pageTitle = toState.data.pageTitle + ' | ItsNow' ;
    }
  });
});

var securityService = angular.module('SecurityService', []);

securityService.factory('Session', ['$resource',
  function($resource){
    return $resource("api/session", {}, {
      create: {method: 'POST'},/* login */
      destroy: {method: 'DELETE'},/* logout */
      current: {method: 'GET'}, /* current session */
      profile: {method: 'GET'}  /* current user profile */
    });
  }
]);

securityService.factory('Password', ['$resource'],
  function($resource){
    return $resource("api/password", {}, {
      forgot: {method: 'POST'},
      reset: {method: 'PUT'}
    });
  }
);

securityService.factory('User', ['$resource'],
  function($resource){
    return $resource('api/users/:userId.json', {}, {
      signup: {url: 'users', method: 'POST'}
    });
  }
);

