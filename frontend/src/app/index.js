var csrfValue = function (req) {
    return (req.body && req.body._csrf)
        || (req.query && req.query._csrf)
        || (req.headers['X-CSRF-TOKEN'])
        || (req.headers['X-XSRF-TOKEN']);
};

angular.module('ItsNow', [
    'templates-app',
    'templates-common',
    'ItsNow.Home',
    'ItsNow.About',
    'ui.router'
])

    .controller('AppCtrl', function AppCtrl($scope, $location) {
        $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            if (angular.isDefined(toState.data.pageTitle)) {
                $scope.pageTitle = toState.data.pageTitle + ' | ItsNow';
            }
        });
    })

    .run(function run() {
    })

;

var indexApp = angular.module('ItsNow.Index', ['menuApp', 'userApp', 'slaApp', 'contractApp', 'ui.router', 'templates-app']);

// 菜单路由设定
indexApp.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/index');

    $stateProvider
        .state('index', {
            url: '/index',
            templateUrl: 'main/main-container.tpl.html'
        })
        .state('index.user', {
            url: '/user',
            templateUrl: 'user/user-list.tpl.html'
        })
        .state('index.sla', {
            url: '/sla',
            templateUrl: 'sla/sla-list.tpl.html'
        })
        .state('index.contract', {
            url: '/contract',
            templateUrl: 'contract/contract-list.tpl.html'
        });

});

