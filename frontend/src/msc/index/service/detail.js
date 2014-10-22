angular.module('MscIndex.ServiceCatalog.Detail', ['ngResource']).config(function($stateProvider) {
  return $stateProvider.state('services.catalog.detail', {
    url: '/detail/{sn}',
    templateUrl: 'service/detail-catalog.tpl.jade',
    data: {
      pageTitle: '服务目录信息'
    }
  });
}).factory('GetCatalogService', [
  '$resource', function($resource) {
    return $resource('/admin/api/public_service_catalogs/:sn', {
      sn: '@sn'
    },{update:{method:'PUT'},
       save:{method:'POST'},
       delete:{method:'DELETE'}});
  }
]).controller('CatalogDetailCtrl', [
  '$scope','$state','$location', '$stateParams', 'GetCatalogService', function($scope,$state,$location, $stateParams, catalogService) {

    alert('sn:' + $stateParams.sn);
    if ($stateParams.sn != null && $stateParams.sn != "") {
      $scope.buttonLabel = '编辑';
    } else {
      $scope.buttonLabel = '新建';
    }
    $scope.sn = $stateParams.sn;

    $scope.catalog = catalogService.get({sn:$scope.sn}, function() {
      //alert('got catalog data:'+$scope.catalog);
    });

    $scope.process = function () {
        if($scope.buttonLabel == '编辑') {
            catalogService.update($scope.catalog), function (data) {
                alert('go');
                $state.go('services.catalog');
                alert('go2');
                $location.path('/catalog');
                alert('go3');
            };
        }
        else {
            catalogService.save($scope.catalog),function(){
                alert('go');
                $state.go('services.catalog');
                alert('go2');
                $location.path('/catalog');
                alert('go3');
            };
        }
    };
  }
]);
