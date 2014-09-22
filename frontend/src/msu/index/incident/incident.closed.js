angular.module('MsuIndex.IncidentClosed', ['ngTable', 'ngResource'])
    .config(['$stateProvider',function($stateProvider) {
        $stateProvider
            .state('incidents-closed',{
                url:'/closed',
                templateUrl:'incident/incident.closed.tpl.jade',
                data: {
                    pageTitle: '已关闭故障单'
                }
            })
        ;
    }])
    .factory('ClosedIncidentService', [
        '$resource', function($resource) {
            return $resource("/api/msu-incidents/closed");
        }])
    .filter('formatIncidentStatus', function() {
        return function(status) {
            if (status === 'New') {
                return "新建";
            }
            if (status === 'Assigned') {
                return "已分配";
            }
            if (status === 'Accepted') {
                return "已签收";
            }
            if (status === 'Resolving') {
                return "处理中";
            }
            if (status === 'Resolved') {
                return "已解决";
            }
            if (status === 'Closed') {
                return "已关闭";
            }
        };
    })
    .filter('formatTime', function() {
        return function(time) {
            var date;
            date = new Date(time);
            return date.toLocaleString();
        };
    })
    .controller('ClosedIncidentListCtrl', [
        '$scope', '$location', '$timeout', 'ngTableParams', 'ClosedIncidentService', function($scope, $location, $timeout, ngTableParams, closedIncidentService) {
            var args, options;
            options = {
                page: 1,
                size: 10
            };
            args = {
                total: 0,
                getData: function($defer, params) {
                    $location.search(params.url());
                    return closedIncidentService.query(params.url(), function(data, headers) {
                        return $timeout(function() {
                            params.total(headers('total'));
                            return $defer.resolve($scope.incidents = data);
                        }, 500);
                    });
                }
            };
            $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args);
            $scope.checkboxes = {
                'checked': false,
                items: {}
            };

            $scope.$watch('checkboxes.checked', function(value) {
                return angular.forEach($scope.incidents, function(item) {
                    if (angular.isDefined(item.id)) {
                        return $scope.checkboxes.items[item.id] = value;
                    }
                });
            });
            return $scope.$watch('checkboxes.items', function(values) {
                var checked, total, unchecked;
                if (!$scope.incidents) {
                    return;
                }
                checked = 0;
                unchecked = 0;
                total = $scope.incidents.length;
                angular.forEach($scope.incidents, function(item) {
                    checked += $scope.checkboxes.items[item.id] || 0;
                    return unchecked += (!$scope.checkboxes.items[item.id]) || 0;
                });
                if ((unchecked === 0) || (checked === 0)) {
                    $scope.checkboxes.checked = checked === total;
                }
                return angular.element(document.getElementById("select_all")).prop("indeterminate", checked !== 0 && unchecked !== 0);
            }, true);
        }
    ])
;
