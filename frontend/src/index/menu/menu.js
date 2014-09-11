/**
 * Created by User on 2014/7/23.
 */
var appMenu = angular.module('Index.Menu', ['ui.tree']);

appMenu.factory('MenuService', ['$resource', function ($resource) {
    return $resource('/api/menu_items');
}]);

var menuCtrl = appMenu.controller('MenuCtrl', ['$scope', 'MenuService', function ($scope, menuService) {

    $scope.topMenuItems = menuService.query();

    $scope.toggle = function(scope) {
        if(scope.collapse){
            scope.expand();
        }else{
            scope.collapse();
        }
    };

}]);

