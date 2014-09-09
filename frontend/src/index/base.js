angular.module('Itsnow.Index', [
    'ui.router',
    'ngResource',
    'ngLocale',
    'Lib.Interceptor',
    'Lib.Templates',
    'Index.Templates',
    'Index.Menu',
    'Index.Dialog',
    'Index.Table'
])
  .config(function ($httpProvider, $stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('');

        $stateProvider
            .state('index', {
                url: '',
                templateUrl: 'main/main-container.tpl.html'
            });

        var menuList = [];
        $.ajax({
            async: false,
            type : "GET",
            url : "/api/menu_items?tree=false",
            dataType : 'json',
            success : function(data) {
                menuList = data;
            }
        });

        for(var i =0 ;i < menuList.length; i++){
            var item = menuList[i];
            $stateProvider.state(item.state, {
                    url: item.url,
                    templateUrl: item.templateUrl
                });
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

  .controller('IndexCtrl', ['$scope',  'SessionService', '$window',
    function ($scope, sessionService, $window) {
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
