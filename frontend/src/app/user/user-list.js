/**
 * Created by User on 2014/7/21.
 */
var userApp = angular.module('userApp', ['ngGrid','ngResource']);

var UserListCtrl = ['$scope', '$http', 'UserService', function ($scope, $http, UserService) {
    $scope.mySelections = [];
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

    // 编辑按钮
    $scope.editableInPopup = '<button id="editBtn" type="button" ng-click="edit(row)" >Edit</button> ';

    $scope.edit = function edit(row){
        console.log("Here I need to know which button was selected " + row.entity.name)
    }

    $scope.setPagingData = function (data, page, pageSize) {
        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
        $scope.users = pagedData;
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        setTimeout(function () {
            var data;
            if (searchText) {
                var ft = searchText.toLowerCase();
                UserService.list(function(largeLoad){
                    data = largeLoad.filter(function (item) {
                        return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                    });
                    $scope.setPagingData(data, page, pageSize);
                });
            } else {
                UserService.list(function(largeLoad){
                    $scope.setPagingData(largeLoad, page, pageSize);
                });
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

    // 用户列表
    $scope.gridUserList = {
        data: 'users',
        enablePaging: true,
        showFooter: true,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions,
        multiSelect: false,
        selectedItems: $scope.mySelections,
        columnDefs: [
            {field: 'id', displayName: 'ID'},
            {field: 'name', displayName: 'Name'},
            {field: 'nickName', displayName: 'NickName'},
            {displayName:'操作',cellTemplate:$scope.editableInPopup}
        ]
    };

}];

userApp.controller('UserListCtrl', UserListCtrl);

// 封装$http
userApp.factory('UserService', ['$resource', function ($resource) {
    return $resource('/api/users', null, {
        list: {
            method: 'POST',
            isArray: true
        }
    });
}]);