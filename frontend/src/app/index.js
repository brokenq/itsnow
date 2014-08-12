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
        })
        // 用户
        .state('index.user', {
            url: '/user',
            templateUrl: 'user/list-user.tpl.html'
        })
        // SLA
        .state('index.sla', {
            url: '/sla',
            templateUrl: 'sla/list-sla.tpl.html'
        })
        // 合同
        .state('index.contract', {
            url: '/contract',
            templateUrl: 'contract/list-contract.tpl.html'
        })
        // 故障单列表
        .state('index.list-trouble-ticket', {
            url: '/list-trouble-ticket',
            templateUrl: 'trouble-ticket/list-trouble-ticket.tpl.html'
        })
        // 新建故障单
        .state('index.new-trouble-ticket', {
            url: '/new-trouble-ticket',
            templateUrl: 'trouble-ticket/new-trouble-ticket.tpl.html'
        })
        // 签收故障单
        .state('index.accept-trouble-ticket', {
            url: '/accept-trouble-ticket',
            templateUrl: 'trouble-ticket/accept-trouble-ticket.tpl.html'
        })
        // 分析故障单
        .state('index.analysis-trouble-ticket', {
            url: '/analysis-trouble-ticket',
            templateUrl: 'trouble-ticket/analysis-trouble-ticket.tpl.html'
        })
        // 处理故障单
        .state('index.process-trouble-ticket', {
            url: '/process-trouble-ticket',
            templateUrl: 'trouble-ticket/process-trouble-ticket.tpl.html'
        });
});

// 获取用户信息工厂
indexApp.factory('LoginOutService', ['$resource', function ($resource) {
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

indexApp.controller('indexCtrl', ['$scope', '$location',  'LoginOutService', function ($scope, $location, LoginOutService) {

//    $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
//        if (angular.isDefined(toState.data.pageTitle)) {
//            $scope.pageTitle = toState.data.pageTitle + ' | ItsNow';
//        }
//    });

    $scope.loginOut = function(){
        LoginOutService.delete(function(){
            $location.path('/login.html');
        });
    };

}]);

// 获取用户信息
indexApp.run(['$rootScope', 'ProfileService', function ($rootScope, ProfileService) {
    ProfileService.get(function (data) {
        $rootScope.user = data;
    });
}]);
