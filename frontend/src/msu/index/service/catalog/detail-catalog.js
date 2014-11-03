angular.module('Service.Catalog.Private.Detail', ['ngResource','Lib.Feedback'])
  .config(function($stateProvider) {
    return $stateProvider.state('services.catalog.private.detail', {
        url: '/detail/{sn}/{action}',
        templateUrl: 'service/catalog/detail-catalog.tpl.jade',
        data: {
        pageTitle: '服务目录信息'
        }
    });
    })
  .factory('CatalogService', [
  '$resource', function($resource) {
    return $resource('/api/private_service_catalogs/:sn',{}, {
        get: { method: 'GET', params: {sn: '@sn'}},
        update:{method:'PUT', params: {sn: '@sn'}},
        save:{method:'POST'},
        remove:{method:'DELETE'}, params: {sn: '@sn'}});
  }
]).controller('CatalogDetailCtrl', [
  '$scope','$state','$location', '$stateParams', 'CatalogService','Feedback', function($scope,$state,$location, $stateParams, catalogService,feedback) {

    $scope.sn = $stateParams.sn;
    $scope.action = $stateParams.action;
    if ($scope.action == 'create' ) {
        $scope.buttonLabel = '创建';
        $scope.catalog = {parentId:null,level:1,publicId:null};
        if($scope.sn !== null && $scope.sn !== '' && $scope.sn !== undefined){
            catalogService.get({sn:$scope.sn}, function(data) {
                $scope.parentCatalog = data;
                $scope.catalog.parentId = $scope.parentCatalog.id;
                $scope.catalog.level = $scope.parentCatalog.level + 1;
            });
        }
    }else{
      $scope.buttonLabel = '更新';
      $("#catalog_sn").attr("readonly","true");
      catalogService.get({sn:$scope.sn}, function(data) {
          $scope.catalog = data;
      });
    }

    $scope.process = function () {
        $scope.catalog.$promise = undefined;
        $scope.catalog.$resolved = undefined;
        if($scope.buttonLabel == '更新') {
            catalogService.update({sn: $scope.catalog.sn},$scope.catalog, function () {
                feedback.success("更新服务目录'" +$scope.catalog.sn + "'成功");
                $state.go('services.catalog.private');
            },function(resp){
                feedback.error("更新服务目录'" +$scope.catalog.sn + "'失败");
            });
        }
        else {
            catalogService.save($scope.catalog,function(){
                feedback.success("创建服务目录'" +$scope.catalog.sn + "'成功");
                $state.go('services.catalog.private');
            },function(resp){
                feedback.error("创建服务目录'" +$scope.catalog.sn + "'失败");
            });
        }
    };

    $scope.reset = function () {
        $state.go('services.catalog.private');
    };
  }
]);
