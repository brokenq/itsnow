angular.module('System.Staff', ['ngTable', 'ngResource', 'Lib.Feedback'])

    .config(function ($stateProvider) {
        $stateProvider.state('staff', {
            url: '/staff',
            templateUrl: 'system/staff/list.tpl.jade',
            data: {pageTitle: '员工管理'}
        });
    })

    .factory('StaffService', ['$resource', function ($resource) {
        return $resource("/api/staffs/:no", {}, {
            query: { method: 'GET', params: {keyword: '@keyword'}, isArray: true},
            get: { method: 'GET', params: {no: '@no'}},
            save: { method: 'POST'},
            update: { method: 'PUT', params: {no: '@no'}},
            remove: { method: 'DELETE', params: {no: '@no'}}
        });
    }])

    .filter('staffStatusFilter', function () {
        return function (input) {
            if (input === '1') {
                return "在职";
            } else if (input === '0') {
                return "离职";
            } else {
                return "无";
            }
        };
    })

    .controller('StaffListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'StaffService', 'ActionService', 'Feedback',
        function ($scope, $location, $timeout, NgTableParams, staffService, ActionService, feedback) {

            var options = {
                page: 1,           // show first page
                count: 5           // count per page
            };
            var args = {
                total: 0,
                getData: function ($defer, params) {
                    $location.search(params.url()); // put params in url
                    staffService.query(params.url(), function (data, headers) {
                            $timeout(function () {
                                    params.total(headers('total'));
                                    $defer.resolve($scope.staffs = data);
                                },
                                500
                            );
                        }
                    );
                }
            };
            $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args);
            $scope.checkboxes = { 'checked': false, items: {} };

            $scope.getStaffByNo = function (no) {
                for (var i in $scope.staffs) {
                    var staff = $scope.staffs[i];
                    if (staff.no === no) {
                        return staff;
                    }
                }
            };
            $scope.actionService = new ActionService({watch: $scope.checkboxes.items, mapping: $scope.getStaffByNo});

            // watch for check all checkbox
            $scope.$watch('checkboxes.checked', function (value) {
                angular.forEach($scope.staffs, function (item) {
                    if (angular.isDefined(item.no)) {
                        $scope.checkboxes.items[item.no] = value;
                    }
                });
            });

            $scope.remove = function (staff) {
                staffService.remove({no: staff.no}, function () {
                    feedback.success("删除员工'" + staff.name + "'成功");
                    delete $scope.checkboxes.items[staff.no];
                    $scope.tableParams.reload();
                },function(resp){
                    feedback.error("删除员工'" + staff.name + "'失败", resp);
                });
            };

            $scope.search = function ($event) {
                if ($event.keyCode === 13) {
                    staffService.query({keyword: $event.currentTarget.value}).then(function (data) {
                        $scope.staffs = data;
                    });
                }
            };

        }
    ]);

