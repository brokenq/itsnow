/**
 * Created by User on 2014/7/23.
 */
var menuApp = angular.module('ItsNow.Menu', ['ui.tree']);

var menuCtrl = menuApp.controller('menuCtrl', ['$scope', 'MenuService', function ($scope, MenuService) {

    $scope.menuList = MenuService.list({tree:true});

    $scope.toggle = function(scope) {
        if(scope.collapse){
            scope.expand();
        }else{
            scope.collapse();
        }
    };

}]);

menuApp.factory('MenuService', ['$resource', function ($resource) {
    return $resource('/api/menu_items', null, {
        list: {
            method: 'GET',
            isArray: true
        }
    });
}]);
