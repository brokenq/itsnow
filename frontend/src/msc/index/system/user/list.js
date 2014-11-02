angular.module('MscIndex.User', ['ngTable', 'ngResource', 'MscIndex.User.Detail', 'dnt.action.service']).config(function ($stateProvider) {
    return $stateProvider.state('user', {
        url: '/user',
        templateUrl: 'system/user/list.tpl.jade',
        data: {
            pageTitle: '用户管理'
        }
    });
})
    .filter('formatUser', function () {
    return function (user) {
        if (user.name === user.username) {
            return user.name;
        }
        return user.name + "(" + user.username + ")";
    };
})
    .factory('UserService', [
        '$resource', function ($resource) {
            return $resource("/admin/api/users/:username",{},{
                get: { method: 'GET', params: {username: '@username'}},
                save: { method: 'POST'},
                update: { method: 'PUT', params: {username: '@username'}},
                query: { method: 'GET', isArray: true},
                remove: { method: 'DELETE', params: {username: '@username'}}
            });
        }
    ])
    .controller('UserListCtrl', [
        '$scope', '$location', '$timeout', '$state', 'ngTableParams', 'UserService', 'ActionService', function ($scope, $location, $timeout, $state, NgTableParams, userService, ActionService) {
            var args, options;
            options = {
                page: 1,
                count: 10
            };
            args = {
                total: 0,
                getData: function ($defer, params) {
                    $location.search(params.url());
                    return userService.query(params.url(), function (data, headers) {
                        return $timeout(function () {
                            params.total(headers('total'));
                            return $defer.resolve($scope.users = data);
                        }, 500);
                    });
                }
            };
            $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args);
            $scope.selection = {
                checked: false,
                items: {}
            };
            $scope.getUserById = function (id) {
                var user, _i, _len, _ref;
                _ref = $scope.users;
                for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                    user = _ref[_i];
                    if (user.id === Number(id)) {
                        return user;
                    }
                }
            };
            $scope.actionService = new ActionService({
                watch: $scope.selection.items,
                mapping: $scope.getUserById
            });
            $scope.$watch('selection.checked', function (value) {
                return angular.forEach($scope.users, function (item) {
                    if (angular.isDefined(item.id)) {
                        return $scope.selection.items[item.id] = value;
                    }
                });
            });
            return $scope.$watch('selection.items', function (values) {
                var checked, total, unchecked;
                if (!$scope.users) {
                    return;
                }
                checked = 0;
                unchecked = 0;
                total = $scope.users.length;
                angular.forEach($scope.users, function (item) {
                    checked += $scope.selection.items[item.id] || 0;
                    return unchecked += (!$scope.selection.items[item.id]) || 0;
                });
                if ((unchecked === 0) || (checked === 0)) {
                    $scope.selection.checked = checked === total;
                }
                return angular.element(document.getElementById("select_all")).prop("indeterminate", checked !== 0 && unchecked !== 0);
            }, true);
        }
    ]);
