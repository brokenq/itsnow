// List System
angular.module('System.Staff', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('staff', {
            url: '/staff',
            templateUrl: 'system/staff/list.tpl.jade',
            data: {pageTitle: '员工管理'}
        });
    })

    .factory('StaffService', ['$resource', function ($resource) {
        return $resource("/api/staffs");
    }])

    .filter('staffStatusFilter', function () {
        var staffStatusFilter = function (input) {
            if (input === '1') {
                return "在职";
            } else {
                return "离职";
            }
        };
        return staffStatusFilter;
    })

    .controller('StaffListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'StaffService', function ($scope, $location, $timeout, NgTableParams, staffService) {
        var options = {
            page: 1,           // show first page
            count: 10           // count per page
        };

        var args = {
            total: 0,
            getData: function ($defer, params) {
                $location.search(params.url()); // put params in url
                staffService.query(params.url(), function (data, headers) {
                        $timeout(function () {
                                params.total(headers('total'));
                                $defer.resolve($scope.staff = data);
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
            angular.forEach($scope.staff, function (item) {
                if (angular.isDefined(item.sn)) {
                    $scope.checkboxes.items[item.sn] = value;

                }
            });
        });

        // watch for data checkboxes
        $scope.$watch('checkboxes.items', function (values) {
                if (!$scope.staff) {
                    return;
                }
                var checked = 0;
                var unchecked = 0;
                var total = $scope.staff.length;
                angular.forEach($scope.staff, function (item) {
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

