// List System
angular.module('System.WorkTime', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('worktime', {
            url: '/worktime',
            templateUrl: 'system/work_time/list.tpl.jade',
            data: {pageTitle: '工作时间管理'}
        });
    })

    .factory('WorkTimeService', ['$resource', function ($resource) {
        return $resource(" /api/work_times/:sn",{},{
            get: { method: 'GET', params: {sn: '@sn'}},
            save: { method: 'POST'},
            update: { method: 'PUT', params: {sn: '@sn'}},
            query: { method: 'GET', isArray: true},
            remove: { method: 'DELETE', params: {sn: '@sn'}}
           // list: { method: 'GET', params: {sn: 'code',code:'@code'}, isArray: true}
        });
    }
    ])

    .controller('WorkTimeListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'WorkTimeService', 'ActionService',function ($scope, $location, $timeout, NgTableParams, workTimeService,ActionService) {
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
                                $defer.resolve($scope.workTimes = data);
                            },
                            500
                        );
                    }
                );
            }
        };
        $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args);
        $scope.checkboxes = { 'checked': false, items: {} };
        $scope.getWorkTimeBySn  = function(sn){
            for(var i in $scope.workTimes){
                var worktime = $scope.workTimes[i];
                if(worktime.sn===sn){
                    $scope.worktime = worktime;
                    return worktime;
                }
            }
        };
        $scope.actionService = new ActionService({watch: $scope.checkboxes.items, mapping: $scope.getWorkTimeBySn});
        // watch for check all checkbox
        $scope.deleteWorkTime = function (worktime) {
            workTimeService.remove({sn: worktime.sn},function(){
                $scope.tableParams.reload();
            });
        };
        $scope.refresh=function(){
            $scope.tableParams.reload();
        };
        $scope.$watch('checkboxes.checked', function (value) {
            angular.forEach($scope.workTimes, function (item) {
                if (angular.isDefined(item.sn)) {
                    $scope.checkboxes.items[item.sn] = value;

                }
            });
        });

        // watch for data checkboxes
        $scope.$watch('checkboxes.items', function (values) {
                if (!$scope.workTimes) {
                    return;
                }
                var checked = 0;
                var unchecked = 0;
                var total = $scope.workTimes.length;
                angular.forEach($scope.workTimes, function (item) {
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

