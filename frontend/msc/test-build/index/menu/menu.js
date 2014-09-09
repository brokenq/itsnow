/**
 * Created by User on 2014/7/23.
 */
var appMenu = angular.module('Index.Menu', ['ui.tree']);

appMenu.factory('MenuService', ['$resource', function ($resource) {
    return $resource('/api/menu_items', null, {
        list: {
            method: 'GET',
            isArray: true
        }
    });
}]);

var menuCtrl = appMenu.controller('MenuCtrl', ['$scope', 'MenuService', function ($scope, menuService) {


    $scope.menuList = menuService.list();

    $scope.toggle = function(scope) {
        if(scope.collapse){
            scope.expand();
        }else{
            scope.collapse();
        }
    };

}]);

