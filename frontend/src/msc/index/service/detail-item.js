angular.module('MscIndex.ServiceCatalog.Item', ['ngResource']).config(function($stateProvider) {
  return $stateProvider.state('services.catalog.item', {
    url: '/item/{sn}/{action}',
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
    if ($scope.action == 'create' ) {
        $scope.buttonLabel = '新建';
        $scope.item = {catalog:null};
        if($scope.sn !== null && $scope.sn !== '' && $scope.sn !== undefined){
            $scope.item.catalog = catalogService.get({sn:$scope.sn}, function() {
                //alert('got parent catalog data:'+$scope.parentCatalog);
            });
        }
    }else{
      $scope.buttonLabel = '编辑';
      $("#item_sn").attr("readonly","true");
      $scope.item = itemService.get({sn:$scope.sn,isn:$scope.isn}, function() {
          //alert('got catalog data');
      });

    }

    $scope.reset = function () {
        $state.go('services.catalog');
    };
  }
]);
