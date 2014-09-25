/**
 * Created by User on 2014/7/23.
 */
angular.module('Index.Menu', []).
  controller('MenuCtrl', ['$scope', '$resource', function ($scope, $resource) {
    var menuService = $resource('/api/menu_items');
    $scope.topMenuItems = menuService.query();
    console.log($scope.topMenuItems);
  }]);
