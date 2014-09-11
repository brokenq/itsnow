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
        $urlRouterProvider.otherwise('/index');

        $stateProvider
            .state('index', {
                url: '/index',
                title: '',
                templateUrl: 'main/main-container.tpl.html'
            })
            // 新建故障单
            .state('index.new-trouble-ticket', {
                url: '/new-trouble-ticket',
                title: '新建故障单',
                templateUrl: 'incident/new-trouble-ticket.tpl.html'
            })
            // 签收故障单
            .state('index.accept-trouble-ticket', {
                url: '/accept-trouble-ticket',
                title: '签收故障单',
                templateUrl: 'incident/accept-trouble-ticket.tpl.html'
            })
            // 分析故障单
            .state('index.analysis-trouble-ticket', {
                url: '/analysis-trouble-ticket',
                title: '分析故障单',
                templateUrl: 'incident/analysis-trouble-ticket.tpl.html'
            })
            // 处理故障单
            .state('index.process-trouble-ticket', {
                url: '/process-trouble-ticket',
                title: '分析故障单',
                templateUrl: 'incident/process-trouble-ticket.tpl.html'
            })
            // 关闭故障单
            .state('index.close-trouble-ticket', {
                url: '/close-trouble-ticket',
                title: '关闭故障单',
                templateUrl: 'incident/close-trouble-ticket.tpl.html'
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
                    url:item.url,
                    title: item.name,
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

            // 根据路由的变动，修改页面标签名称和导航名称
            $rootScope.$on('$stateChangeSuccess',
                function (event, toState, toParams, fromState, fromParams) {
                    $rootScope.title = toState.title;
                    $rootScope.pageTitle = toState.title;
                });

        }]);
