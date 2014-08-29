var csrfValue = function (req) {
    return (req.body && req.body._csrf)
        || (req.query && req.query._csrf)
        || (req.headers['X-CSRF-TOKEN'])
        || (req.headers['X-XSRF-TOKEN']);
};

var indexApp = angular.module('ItsNow.Index', [
    'ItsNow.Interceprot',
    'ItsNow.Menu',
    'ItsNow.User',
    'ItsNow.SLA',
    'ItsNow.Contract',
    'ItsNow.TroubleTicket',
    'ItsNow.MSP.TroubleTicket',
    'ItsNow.Home',
    'ItsNow.About',
    'templates-app',
    'templates-common',
    'ui.router'
]);

// 菜单路由设定
indexApp.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/index');

    $stateProvider
        .state('index', {
            url: '/index',
            templateUrl: 'main/main-container.tpl.html'
        });

    var menuList;
    $.ajax({
        async: false,
        type : "GET",
        url : "/api/menu_items",
        dataType : 'json',
        success : function(data) {
            menuList=data;
        }
    });

    for(var i in menuList){
        $stateProvider
            .state(menuList[i].state, {
                url:menuList[i].url,
                templateUrl: menuList[i].templateUrl
            });
    }

});



// 获取用户信息工厂
indexApp.factory('LogoutService', ['$resource', function ($resource) {
    return $resource('/api/session', null, {
        delete: {method: 'DELETE'}
    });
}])

// 获取用户信息工厂
indexApp.factory('ProfileService', ['$resource', function ($resource) {
    return $resource('/api/profile', null, {
        get: {method: 'GET'}
    });
}])

indexApp.controller('indexCtrl', ['$scope', '$location',  'LogoutService', '$window', function ($scope, $location, LogoutService, $window) {

//    $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
//        if (angular.isDefined(toState.data.pageTitle)) {
//            $scope.pageTitle = toState.data.pageTitle + ' | ItsNow';
//        }
//    });

    $scope.logout = function(){
        LogoutService.delete(function(){
            $window.location.href='/login.html';
        });
    };

}]);

// 获取用户信息
indexApp.run(['$rootScope', 'ProfileService', function ($rootScope, ProfileService) {
    ProfileService.get(function (data) {
        $rootScope.user = data;
    });
}]);
