/**
 * Created by User on 2014/7/21.
 */
var slaApp = angular.module('ItsNow.SLA', ['ngGrid','ngResource']);

var SlaListCtrl = ['$scope', '$http', 'SlaService', function ($scope, $http, SlaService) {
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
                {id: '001', name: '协议1', resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '002', name: '协议2',  resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '003', name: '协议3',  resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '004', name: '协议4',  resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '005', name: '协议5',  resolutionTime: '201407211258', responseTime: '201407211258'}
            ];
            if (searchText) {
//                var ft = searchText.toLowerCase();
//                SlaService.list(function(largeLoad){
//                    data = largeLoad.filter(function (item) {
//                        return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
//                    });
//                    $scope.setPagingData(data, page, pageSize);
//                });
                    $scope.setPagingData(data, page, pageSize);
            } else {
//                SlaService.list(function(largeLoad){
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

    $scope.gridSlaList = {
        data: 'datas',
        enablePaging: true,
        showFooter: true,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions,
        multiSelect: false,
        columnDefs: [
            {field: 'id', displayName: 'ID'},
            {field: 'name', displayName: '协议名称'},
            {field: 'resolutionTime', displayName: '解决时间'}
        ]
    };
}];

slaApp.controller('SlaListCtrl', SlaListCtrl);

// 封装$http
slaApp.factory('SlaService', ['$resource', function ($resource) {
    return $resource('/api/sla', null, {
        list: {
            method: 'GET',
            isArray: true
        }
    });
}]);