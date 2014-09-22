// List System
angular.module('System.WorkTime', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('worktime', {
            url: '/worktime',
            templateUrl: 'system/work_time/list.tpl.jade',
            data: {pageTitle: '角色管理'}
        });
    })

    .factory('WorkTimeService', ['$resource', function ($resource) {
        return $resource("/api/work-times");
    }
    ])

    .controller('WorkTimeListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'WorkTimeService', function ($scope, $location, $timeout, NgTableParams, workTimeService) {
        var options = {
            page: 1,           // show first page
            count: 10           // count per page
        };

        var args = {
            total: 0,
            getData: function ($defer, params) {
                $location.search(params.url()); // put params in url
                workTimeService.query(params.url(), function (data, headers) {
                        $timeout(function () {
                                params.total(headers('total'));
                                $defer.resolve($scope.workTime = data);
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
            angular.forEach($scope.workTime, function (item) {
                if (angular.isDefined(item.sn)) {
                    $scope.checkboxes.items[item.sn] = value;

                }
            });
        });

        // watch for data checkboxes
        $scope.$watch('checkboxes.items', function (values) {
                if (!$scope.workTime) {
                    return;
                }
                var checked = 0;
                var unchecked = 0;
                var total = $scope.workTime.length;
                angular.forEach($scope.workTime, function (item) {
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
