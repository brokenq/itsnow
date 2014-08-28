/**
 * Created by User on 2014/7/23.
 */
var menuApp = angular.module('ItsNow.Menu', ['ui.tree']);

var menuCtrl = menuApp.controller('menuCtrl', ['$scope', 'MenuService', function ($scope, MenuService) {

//    $scope.menuList = [
//        {id: '001', title: '用户', url: 'index.user'},
//        {id: '002', title: 'SLA', url: 'index.sla'},
//        {id: '003', title: '合同', url: 'index.contract'},
//        {id: '004', title: '故障单', subMenu: [
//            {id: '004001', title: 'MSU', url: 'index.list-trouble-ticket'},
//            {id: '004002', title: 'MSP', url: 'index.msp-list-trouble-ticket'}
//        ]}
//    ];

    $scope.menuList = MenuService.list();

    $scope.toggle = function(scope) {
        if(scope.collapse){
            scope.expand();
        }else{
            scope.collapse();
        }
    };

}]);

menuApp.factory('MenuService', ['$resource', function ($resource) {
    return $resource('/api/menu', null, {
        list: {
            method: 'GET',
            isArray: true
        }
    });
}]);
