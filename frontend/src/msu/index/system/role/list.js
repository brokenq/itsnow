// List System
angular.module('System.Role', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('role', {
            url: '/role',
            templateUrl: 'system/role/list.tpl.jade',
            data: {pageTitle: '角色管理'}
        });
    })

    .factory('RoleService', ['$resource', function ($resource) {
        return $resource("/api/roles");
    }
    ])

    .factory('RoleDetailService', ['$resource', function ($resource) {
        return $resource("/api/roles/:name",{name:'@name'});
    }
    ])

    .controller('RoleListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'RoleService', 'RoleDetailService', function ($scope, $location, $timeout, NgTableParams, roleService, roleDetailService) {
        var options = {
            page: 1,           // show first page
            count: 10           // count per page
        };

        var args = {
            total: 0,
            getData: function ($defer, params) {
                $location.search(params.url()); // put params in url
                roleService.query(params.url(), function (data, headers) {
                        $timeout(function () {
                                params.total(headers('total'));
                                $defer.resolve($scope.role = data);
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
            angular.forEach($scope.role, function (item) {
                if (angular.isDefined(item.sn)) {
                    $scope.checkboxes.items[item.sn] = value;

                }
            });
        });

        // watch for data checkboxes
        $scope.$watch('checkboxes.items', function (values) {
                if (!$scope.role) {
                    return;
                }
                var checked = 0;
                var unchecked = 0;
                var total = $scope.role.length;
                angular.forEach($scope.role, function (item) {
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

        var roleName = {name:'null'};
        var detailArgs = {
            total: 0,
            getData: function ($defer, params) {
                $location.search(params.url()); // put params in url
                roleDetailService.query(params.url(), roleName, function (data, headers) {
                        $timeout(function () {
                                params.total(headers('total'));
                                $defer.resolve($scope.detailRoles = data.length>0 ? data[0].details : data);
                            },
                            500
                        );
                    }
                );
            }
        };
        $scope.detailTableParams = new NgTableParams(angular.extend(options, $location.search()), detailArgs);

        $scope.changeSelection = function (role) {
            roleName = {name:role.name};
            $scope.detailTableParams.reload();
        };

    }
    ]);

