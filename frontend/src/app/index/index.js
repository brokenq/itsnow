/**
 * Created by User on 2014/7/24.
 */
var indexApp = angular.module('indexApp', ['menuApp', 'userApp', 'slaApp', 'contractApp', 'ui.router', 'templates-app']);

indexApp.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/index');

    $stateProvider
        .state('index', {
            url: '/index',
            templateUrl: 'index/main-container.tpl.html'
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
