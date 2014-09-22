// List System
angular.module('System.Group', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('group', {
            url: '/group',
            templateUrl: 'system/group/list.tpl.jade',
            data: {pageTitle: '角色管理'}
        });
    })

    .factory('GroupService', ['$resource', function ($resource) {
        return $resource("/api/msp-groups");
    }
    ])

    .factory('GroupDetailService', ['$resource', function ($resource) {
        return $resource("/api/msp-groups/detail/");
    }
    ])

    .controller('GroupListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'GroupService', 'GroupDetailService', function ($scope, $location, $timeout, NgTableParams, groupService, groupDetailService) {
        var options = {
            page: 1,           // show first page
            count: 10           // count per page
        };

        var args = {
            total: 0,
            getData: function ($defer, params) {
                $location.search(params.url()); // put params in url
                groupService.query(params.url(), function (data, headers) {
                        $timeout(function () {
                                params.total(headers('total'));
                                $defer.resolve($scope.group = data);
                            },
                            500
                        );
                    }
                );
            }
        };
        $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args);

        var detailArgs = {
            total: 0,
            getData: function ($defer, params) {
                $location.search(params.url()); // put params in url
                groupDetailService.query(params.url(), function (data, headers) {
                        $timeout(function () {
                                params.total(headers('total'));
                                $defer.resolve($scope.group = data);
                            },
                            500
                        );
                    }
                );
            }
        };


        $scope.checkboxes = { 'checked': false, items: {} };

        // watch for check all checkbox
        $scope.$watch('checkboxes.checked', function (value) {
            angular.forEach($scope.group, function (item) {
                if (angular.isDefined(item.sn)) {
                    $scope.checkboxes.items[item.sn] = value;

                }
            });
        });

        // watch for data checkboxes
        $scope.$watch('checkboxes.items', function (values) {
                if (!$scope.group) {
                    return;
                }
                $scope.detailData={};
                var checked = 0;
                var unchecked = 0;
                var total = $scope.group.length;
                angular.forEach($scope.group, function (item) {
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

        $scope.changeSelection = function(group) {
            console.info(group);
            $scope.detailTableParams = new NgTableParams(angular.extend(options, $location.search()), detailArgs);
        };

    }
    ]);

