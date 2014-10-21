// List System
angular.module('System.Role', ['ngTable', 'ngResource', 'dnt.action.service'])

    .config(function ($stateProvider) {
        $stateProvider.state('system.role', {
            url: '/role',
            templateUrl: 'system/role/list.tpl.jade',
            data: {pageTitle: '角色管理'}
        });
    })

    .factory('RoleService', ['$resource', function ($resource) {
        return $resource("/api/roles/:name", {}, {
            get: { method: 'GET', params: {name: '@name'}},
            save: { method: 'POST'},
            update: { method: 'PUT', params: {name: '@name'}},
            query: { method: 'GET', isArray: true},
            remove: { method: 'DELETE', params: {name: '@name'}},
            getUsers: { method: 'GET', params: {name: 'users'}, isArray: true}
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

    .controller('RoleListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'RoleService', 'ActionService',
        function ($scope, $location, $timeout, NgTableParams, roleService, ActionService) {

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
                                    $defer.resolve($scope.roles = data);
                                },
                                500
                            );
                        }
                    );
                }
            };
            $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args);

            $scope.selection = {checked: false, items: {}};
            $scope.getRoleByName = function (name) {
//            var idInt = parseInt(id, 10);
                var i;
                for (i in $scope.roles) {
                    var role = $scope.roles[i];
                    if (role.name === name) {
                        return role;
                    }
                }
            };
            $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getRoleByName});

            // watch for check all checkbox
            $scope.$watch('selection.checked', function (value) {
                angular.forEach($scope.roles, function (item) {
                    if (angular.isDefined(item.name)) {
                        $scope.selection.items[item.name] = value;
                    }
                });
            });

            $scope.deleteRole = function (role) {
                roleService.remove({name: role.name},function(){
//                    $location.path('/system/role');
                    console.log('remove function backcalling!');
                    $scope.tableParams.reload();
                    console.log('remove function backcalled!');
                });
            };

        }
    ]);

