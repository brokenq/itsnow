// List System
angular.module('System.Department', ['ngTable', 'ngResource', 'ui.tree'])

    .config(function ($stateProvider) {
        $stateProvider.state('department', {
            url: '/department',
            templateUrl: 'system/department/list.tpl.jade',
            data: {pageTitle: '部门管理'}
        });
    })

    .factory('DepartmentService', ['$resource', function ($resource) {
        return $resource("/api/departments/:sn", {}, {
            query : {method:'GET', params:{isTree:true}, isArray:true},
            show : {method:'GET', params:{sn:'@sn'}}
        });
    }
    ])

    // 过滤拼接地点后的最后一个逗号
    .filter('siteFilter', function () {
        var siteFilter = function (input) {
            var name = '';
            if (input !== null && input !== undefined) {
                for (var i = 0; i < input.length; i++) {
                    name += input[i].name + ',';
                }
                name = name.substring(0, name.length - 1);
            }
            return name || '无';
        };
        return siteFilter;
    })

    .controller('DepartmentListCtrl', ['$scope', 'DepartmentService', function ($scope, departmentService) {

        var promise = departmentService.query().$promise;
        promise.then(function (data) {
            $scope.departments = data;
        });

        $scope.show = function(sn){
            console.log("sn:"+sn);
            promise = departmentService.show({sn:sn}).$promise;
            promise.then(function (data) {
                $scope.departmentDetail = data;
            });
        };

        $scope.toggle = function(scope) {
            scope.toggle();
        };

    }
    ]);

