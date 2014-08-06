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

app.run(function($rootScope,csrf){
    $rootScope.data = csrf.load();
    $rootScope.value = 'testing';
});

app.factory('csrf', function ($http) {
    return {
        load: function () {
            $http.get('security/csrf').then(function (response) {
                console.log("CSRF Success. headerName:" + response.data.headerName + " token:" + response.data.token);
                $http.defaults.headers.common[response.data.headerName] = response.data.token;
            });
        }
    };
});

app.config(function($stateProvider, $urlRouterProvider){
    $urlRouterProvider.otherwise("/authenticate");
});

app.controller('LoginCtrl', function($scope){
    $scope.$on('$stateChangeSuccess', function LoginCtrl(toState){
        if ( angular.isDefined( toState.data.pageTitle ) ) {
            $scope.pageTitle = toState.data.pageTitle + ' | ItsNow' ;
        }
    });
});

var securityService = angular.module('SecurityService', []);

securityService.factory('Session', ['$resource',
    function ($resource) {
        return $resource("/api/session?username=:username&password=:password", {username: '@username', password: '@password'}, {
            create: {method: 'POST'}, /* login */
            destroy: {method: 'DELETE'}, /* logout */
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
        return $resource('api/users/:userId', {}, {
            signup: {url: 'users', method: 'POST', params:{userId:'@userId'}}
        });
    }
);

// 拦截器（只拦截POST请求）
app.factory('SessionInjector', ['$injector',
    function ($injector) {
        return {
            response: function (response) {
                console.log('拦截请求！');
                if (response.config.method === "POST") {
                    console.log("response.config.method: " + response.config.method);
                    var csrf = $injector.get('csrf');
                    csrf.load();
                }
                return response;
            }
        };
    }]);

// 注入拦截器
app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('SessionInjector');
}]);