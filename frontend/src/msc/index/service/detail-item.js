angular.module('MscIndex.ServiceCatalog.Item', ['ngResource']).config(function($stateProvider) {
  return $stateProvider.state('services.catalog.item', {
    url: '/{sn}/items/{isn}/{action}',
    templateUrl: 'service/detail-item.tpl.jade',
    data: {
      pageTitle: '服务项信息'
    }
  });
}).factory('ItemService', [
    '$resource', function($resource) {
        return $resource('/admin/api/public_service_catalogs/:sn/items/:isn',{}, {
            get: { method: 'GET', params: {sn: '@sn',isn:'@isn'}},
            update:{method:'PUT', params: {sn: '@sn',isn:'@isn'}},
            save:{method:'POST', params: {sn: '@sn'}},
            remove:{method:'DELETE'}, params: {sn: '@sn',isn:'@isn'}});
    }
]).controller('ItemDetailCtrl', [
  '$scope','$state','$location', '$stateParams','CatalogService', 'ItemService', function($scope,$state,$location, $stateParams,catalogService, itemService) {

    $scope.sn = $stateParams.sn;
    $scope.isn = $stateParams.isn;
    $scope.action = $stateParams.action;
    console.log('sn:'+$scope.sn+' isn:'+$scope.isn+' action:'+$scope.action);
    if ($scope.action == 'create' ) {
        $scope.buttonLabel = '新建';
        $scope.item = {catalog:null};
        if($scope.sn !== null && $scope.sn !== '' && $scope.sn !== undefined){
            $scope.item.catalog = catalogService.get({sn:$scope.sn}, function() {
                console.log($scope.item.catalog);
                $scope.item.catalog.$promise = undefined;
                $scope.item.catalog.$resolved = undefined;
            });
        }
    }else{
      $scope.buttonLabel = '编辑';
      $("#item_sn").attr("readonly","true");
      $scope.item = itemService.get({sn:$scope.sn,isn:$scope.isn}, function() {
          console.log($scope.item);
      });
    }

    $scope.process = function () {
        $scope.item.$promise = undefined;
        $scope.item.$resolved = undefined;
        if($scope.buttonLabel == '编辑') {
            console.log($scope.item);
            itemService.update({sn: $scope.sn,isn:$scope.isn},$scope.item, function (data) {
                $state.go('services.catalog');
            });
        }
        else {
            console.log($scope.item);
            itemService.save({sn:$scope.sn},$scope.item,function(){
                $state.go('services.catalog');
            });
        }
    };

    $scope.reset = function () {
        $state.go('services.catalog');
    };
  }
]);
