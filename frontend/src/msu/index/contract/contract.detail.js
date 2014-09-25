angular.module('MsuIndex.ContractCreate', ['ngResource']).config(function($stateProvider) {
  return $stateProvider.state('contract-create', {
    url: '/contract-create',
    templateUrl: 'contract/contract.create.tpl.html',
    data: {
      pageTitle: '新建合同'
    }
  });
})
    // 新建合同
    .factory('CreateContractService', ['$resource', function ($resource) {
        return $resource('/api/contracts', {}, {
            create: {method: 'POST'}
        });
    }])

    .controller('CreateContractCtrl', ['$rootScope','$scope','$state','$stateParams', 'CreateContractService', '$location',
        function ($rootScope,$scope,$state,$stateParams, createContractService, $location) {

            $scope.contract = {msuStatus:'Draft',msuAccountId:2,mspStatus:'Draft'};

            //查询服务目录

            $scope.createContract = function () {
                //alert("sn:"+$scope.contract.sn);
                createContractService.create($scope.contract, function () {
                    //alert('新建完成');
                    $location.path('/contracts/contract');
                });
            }

        }]);
