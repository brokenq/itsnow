angular.module('MscIndex.ServiceCatalog.Item', ['ngResource','Lib.Feedback']).config(function($stateProvider) {
  return $stateProvider.state('services.catalog.item', {
    url: '/{sn}/items/{isn}/{action}',
    templateUrl: 'service/catalog/detail-item.tpl.jade',
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
  '$scope','$state','$location', '$stateParams','CatalogService', 'ItemService','Feedback',
  function($scope,$state,$location, $stateParams,catalogService, itemService,feedback) {

    $scope.sn = $stateParams.sn;
    $scope.isn = $stateParams.isn;
    $scope.action = $stateParams.action;
    console.log('sn:'+$scope.sn+' isn:'+$scope.isn+' action:'+$scope.action);
    if ($scope.action == 'create' ) {
        $scope.buttonLabel = '创建';
        $scope.item = {catalog:null};
        if($scope.sn !== null && $scope.sn !== '' && $scope.sn !== undefined){
            $scope.item.catalog = catalogService.get({sn:$scope.sn}, function() {
                $scope.item.catalog.$promise = undefined;
                $scope.item.catalog.$resolved = undefined;
            });
        }
    }else{
      $scope.buttonLabel = '更新';
      $("#item_sn").attr("readonly","true");
      $scope.item = itemService.get({sn:$scope.sn,isn:$scope.isn}, function() {
          console.log($scope.item);
      });
    }

    $scope.process = function () {
        $scope.item.$promise = undefined;
        $scope.item.$resolved = undefined;
        if($scope.buttonLabel == '更新') {
            itemService.update({sn: $scope.sn,isn:$scope.isn},$scope.item, function () {
                feedback.success('更新服务项'+$scope.catalog.sn+'成功');
                $state.go('services.catalog');
            },function(resp){
                feedback.error('更新服务项'+$scope.catalog.sn+'失败');
            });
        }
        else {
            itemService.save({sn:$scope.sn},$scope.item,function(){
                feedback.success('创建服务项'+$scope.sn+'成功');
                $state.go('services.catalog');
            },function(resp){
                feedback.error('创建服务项'+$scope.sn+'失败');
            });
        }
    };

    $scope.reset = function () {
        $state.go('services.catalog');
    };
  }
]);
