// List System
angular.module('System.Department', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('department', {
            url: '/department',
            templateUrl: 'system/department/list.tpl.jade',
            data: {pageTitle: '部门管理'}
        });
    })

    .factory('DepartmentService', ['$resource', function ($resource) {
        return $resource("/api/departments");
    }
    ])

    // 过滤拼接地点后的最后一个逗号
    .filter('siteFilter', function () {
        var siteFilter = function (input) {
            var name = '';
            for (var i = 0; i < input.length; i++) {
                name += input[i].name + ',';
            }
            name = name.substring(0, name.length - 1);
            return name;
        };
        return siteFilter;
    })

    .controller('DepartmentListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'DepartmentService', function ($scope, $location, $timeout, NgTableParams, departmentService) {
        var options = {
            page: 1,           // show first page
            count: 10           // count per page
        };

        var args = {
            total: 0,
            getData: function ($defer, params) {
                $location.search(params.url()); // put params in url
                departmentService.query(params.url(), function (data, headers) {
                        $timeout(function () {
                                params.total(headers('total'));
                                $defer.resolve($scope.department = data);
                            },
                            500
                        );
                    }
                );
            }
        };
        $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args);
        $scope.checkboxes = { 'checked': false, items: {} };

        // watch for check all checkbox
        $scope.$watch('checkboxes.checked', function (value) {
            angular.forEach($scope.department, function (item) {
                if (angular.isDefined(item.sn)) {
                    $scope.checkboxes.items[item.sn] = value;

                }
            });
        });

        // watch for data checkboxes
        $scope.$watch('checkboxes.items', function (values) {
                if (!$scope.department) {
                    return;
                }
                var checked = 0;
                var unchecked = 0;
                var total = $scope.department.length;
                angular.forEach($scope.department, function (item) {
                    checked += ($scope.checkboxes.items[item.sn]) || 0;
                    unchecked += (!$scope.checkboxes.items[item.sn]) || 0;
                });
                if ((unchecked === 0) || (checked === 0)) {
                    $scope.checkboxes.checked = (checked == total);
                }
                // grayed checkbox
                angular.element(document.getElementById("select_all")).prop("indeterminate", (checked !== 0 && unchecked !== 0));
            },
           true
        );

    }
    ]);

