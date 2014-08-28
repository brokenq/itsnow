var ItsnowApp = angular.module('Itsnow.App', [
    'Common.Interceptor',
    'Common.Templates',
    'App.Menu',
    'App.Dialog',
    'App.Table',
    'App.Templates',
    'ui.router'
]);

// GET /api/menu_items?tree=false


// 菜单路由设定
ItsnowApp.config(function (MenuService, $stateProvider, $urlRouterProvider) {
  var menus = MenuService.list({tree: false});

  $urlRouterProvider.otherwise('/');
    for( var i=0; i < menus.length; i++  ){
      var item = menus[i];
      $stateProvider.state(item.state, {templateUrl: item.templateUrl, url: item.url} );
    }
});

// 登录/登出
ItsnowApp.factory('LogoutService', ['$resource', function ($resource) {
    return $resource('/api/session', null, {
        delete: {method: 'DELETE'}
    });
}]);

// 获取用户信息工厂
ItsnowApp.factory('ProfileService', ['$resource', function ($resource) {
    return $resource('/api/profile', null, {
        get: {method: 'GET'}
    });
}]);

ItsnowApp.controller('indexCtrl', ['$scope', '$location',  'LogoutService', '$window',
  function ($scope, $location, LogoutService, $window) {
    $scope.logout = function(){
        LogoutService.delete(function(){
            $window.location.href='/login.html';
        });
    };

}]);

// 获取用户信息
ItsnowApp.run(['$rootScope', 'ProfileService',
  function ($rootScope, ProfileService) {
    ProfileService.get(function (data) {
        $rootScope.user = data;
    });
}]);
