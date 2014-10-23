angular.module('MscIndex.ServiceCatalog.Detail', ['ngResource']).config(function($stateProvider) {
  return $stateProvider.state('services.catalog.detail', {
    url: '/detail/{sn}/{action}',
    templateUrl: 'service/detail.tpl.jade',
    data: {
      pageTitle: '服务目录信息'
    }
  });
}).factory('CatalogService', [
  '$resource', function($resource) {
    return $resource('/admin/api/public_service_catalogs/:sn',{}, {
        get: { method: 'GET', params: {sn: '@sn'}},
        update:{method:'PUT', params: {sn: '@sn'}},
        save:{method:'POST'},
        remove:{method:'DELETE'}, params: {sn: '@sn'}});
  }
]).controller('CatalogDetailCtrl', [
  '$scope','$state','$location', '$stateParams', 'CatalogService', function($scope,$state,$location, $stateParams, catalogService) {
    $scope.sn = $stateParams.sn;
    $scope.action = $stateParams.action;
    if ($scope.action == 'create' ) {
        $scope.buttonLabel = '新建';
        $scope.catalog = {parentId:null,level:1};
        if($scope.sn !== null && $scope.sn !== '' && $scope.sn !== undefined){
            $scope.parentCatalog = catalogService.get({sn:$scope.sn}, function() {
                //alert('got parent catalog data:'+$scope.parentCatalog);
                $scope.catalog.parentId = $scope.parentCatalog.id;
                $scope.catalog.level = $scope.parentCatalog.level + 1;
            });
        }
    }else{
      $scope.buttonLabel = '编辑';
      $("#sn").attr("readonly","true");
      $scope.catalog = catalogService.get({sn:$scope.sn}, function() {
          //alert('got catalog data');
      });

    }

    $scope.process = function () {
        $scope.catalog.$promise = undefined;
        $scope.catalog.$resolved = undefined;
        if($scope.buttonLabel == '编辑') {
            catalogService.update({sn: $scope.catalog.sn},$scope.catalog, function (data) {
                $state.go('services.catalog');
            });
        }
        else {
            catalogService.save($scope.catalog,function(){
                $state.go('services.catalog');
            });
        }
    };

    $scope.reset = function () {
        $state.go('services.catalog');
    };
  }
]);
