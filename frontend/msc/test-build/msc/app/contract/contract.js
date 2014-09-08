/**
 * Created by User on 2014/7/21.
 */
var contractApp = angular.module('MscApp.Contract', ['ngGrid','ngResource']);

// 封装$http
contractApp.factory('ContractService', ['$resource', function ($resource) {
  return $resource('/api/contracts', null, {
    list: {
      method: 'GET',
      isArray: true
    }
  });
}]);

var ContractListCtrl = ['$scope', 'ContractService',
  function ($scope, contractService) {
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };
    $scope.totalServerItems = 0;
    $scope.pagingOptions = {
        pageSizes: [10],
        pageSize: 10,
        currentPage: 1
    };
    $scope.setPagingData = function (data, page, pageSize) {
        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
        $scope.datas = pagedData;
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        setTimeout(function () {
            var data = [
                {id: '001', name: '合同1', resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '002', name: '合同2',  resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '003', name: '合同3',  resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '004', name: '合同4',  resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '005', name: '合同5',  resolutionTime: '201407211258', responseTime: '201407211258'}
            ];
            if (searchText) {
//                var ft = searchText.toLowerCase();
//                contractService.list(function(largeLoad){
//                    data = largeLoad.filter(function (item) {
//                        return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
//                    });
//                    $scope.setPagingData(data, page, pageSize);
//                });
                  $scope.setPagingData(data, page, pageSize);
            } else {
//                contractService.list(function(largeLoad){
//                    $scope.setPagingData(largeLoad, page, pageSize);
//                });
                  $scope.setPagingData(data, page, pageSize);
            }
        }, 100);
    };

    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);
    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.gridContractList = {
        data: 'datas',
        enablePaging: true,
        showFooter: true,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions,
        multiSelect: false,
        columnDefs: [
            {field: 'id', displayName: 'ID'},
            {field: 'name', displayName: '合同名称'}
        ]
    };
}];

contractApp.controller('ContractListCtrl', ContractListCtrl);

