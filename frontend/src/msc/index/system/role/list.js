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
                                $defer.resolve($scope.role = data);
                            },
                            500
                        );
                    }
                );
            }
        };
        $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args);

        $scope.selection = {checked: false, items: {}};
        $scope.getUserById  = function(id){
            var idInt = parseInt(id, 10);
            var i;
            for(i in $scope.users){
                var user = $scope.users[i];
                if(user.id===idInt){
                    return user;
                }
            }
        };
        $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getUserById});

        // watch for check all checkbox
        $scope.$watch('selection.checked', function (value) {
            angular.forEach($scope.role, function (item) {
                if (angular.isDefined(item.sn)) {
                    $scope.selection.items[item.sn] = value;
                }
            });
        });

        // watch for data checkboxes
        $scope.$watch('selection.items', function (values) {
                if (!$scope.role) {
                    return;
                }
                var checked = 0;
                var unchecked = 0;
                var total = $scope.role.length;
                angular.forEach($scope.role, function (item) {
                    checked += ($scope.selection.items[item.sn]) || 0;
                    unchecked += (!$scope.selection.items[item.sn]) || 0;
                });
                if ((unchecked === 0) || (checked === 0)) {
                    $scope.selection.checked = (checked == total);
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

