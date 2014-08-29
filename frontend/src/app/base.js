angular.module('Itsnow.App', [
    'ui.router',
    'ngResource',
    'Common.Interceptor',
    'Common.Templates',
    'App.Templates',
    'App.Menu',
    'App.Dialog',
    'App.Table'
])
  .config(function ($httpProvider, $stateProvider, $urlRouterProvider) {
    var menus = [];//$httpProvider.get({url: '/api/menu_items?tree=false', tree: false});

    $urlRouterProvider.otherwise('/');
    for( var i=0; i < menus.length; i++  ){
      var item = menus[i];
      $stateProvider.state(item.state, {templateUrl: item.templateUrl, url: item.url} );
    }
  })

  // 登录/登出
  .factory('SessionService', ['$resource', function ($resource) {
      return $resource('/api/session', null, {
          logout: {method: 'DELETE'}
      });
  }])

  // 获取用户信息工厂
  .factory('ProfileService', ['$resource', function ($resource) {
      return $resource('/api/profile', null, {
          get: {method: 'GET'}
      });
  }])

  .controller('IndexCtrl', ['$scope', '$location',  'SessionService', '$window',
    function ($scope, $location, sessionService, $window) {
      $scope.logout = function(){
        sessionService.logout(function(){
          $window.location.href='/login.html';
        });
      };

  }])

  // 获取用户信息
  .run(['$rootScope', 'ProfileService',
    function ($rootScope, profileService) {
      profileService.get(function (data) {
          $rootScope.user = data;
      });
  }]);
