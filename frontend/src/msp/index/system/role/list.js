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
        return $resource("/api/roles/:name", {}, {
            query: { method: 'GET', isArray:true},
            remove: { method: 'DELETE', params:{name:'@name'}, isArray:true }
        });
    }
    ])

    // 过滤拼接地点后的最后一个逗号
    .filter('colFilter', function () {
        var colFilter = function (input) {
            var name = '';
            if (input !== null && input !== undefined) {
                for (var i = 0; i < input.length; i++) {
                    name += input[i].name + ', ';
                }
                name = name.substring(0, name.length - 2);
            }
            return name || '无';
        };
        return colFilter;
    })

    .controller('RoleListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'RoleService',
        function ($scope, $location, $timeout, NgTableParams, roleService) {

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

        $scope.deleteRole = function () {
            roleService.remove({name:'ROLE_ADMIN'});
        };

    }
    ]);

