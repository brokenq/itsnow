angular.module('Itsnow.Index', [
    'ui.router',
    'ngResource',
    'ngLocale',
    'Lib.Interceptor',
    'Lib.Templates',
    'Index.Templates',
    'Index.Menu',
    'Index.Dialog',
    'Index.Table',
    'jcs-autoValidate'
])
  .config(function ($httpProvider, $stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('dashboard');

        $stateProvider.state('dashboard', {
                url: '/dashboard',
                templateUrl: 'dashboard/dashboard.tpl.jade',
                data: {pageTitle: '总览'}
        });


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

  .controller('IndexCtrl', ['$rootScope', '$scope', '$state',  'SessionService', '$window',
    function ($rootScope, $scope, $state, sessionService, $window) {
      $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
        // Record current breadcrumbs
        var breadcrumb = toState;
        var breadcrumbs = [breadcrumb];
        var last = breadcrumb.name.lastIndexOf('.');
        var pageTitle = (breadcrumb.data || {pageTitle: "未命名"}).pageTitle;
        while(last > 0 ){
          breadcrumb = $state.get(breadcrumb.name.substring(0, last));
          breadcrumbs.push(breadcrumb);
          pageTitle = pageTitle + " | " + (breadcrumb.data || {pageTitle: "未命名"}).pageTitle;
          last = breadcrumb.name.lastIndexOf('.');
        }
        $rootScope.pageTitle = pageTitle;
        $rootScope.breadcrumbs = breadcrumbs.reverse();
      });
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
  }])

  // angular-auto-validate error message
  .run(['defaultErrorMessageResolver',
    function (defaultErrorMessageResolver) {
      defaultErrorMessageResolver.setI18nFileRootPath('assets/json');
      defaultErrorMessageResolver.setCulture('zh-CN');
    }
  ]);
