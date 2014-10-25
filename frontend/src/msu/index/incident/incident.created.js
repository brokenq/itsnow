angular.module('MsuIndex.IncidentCreated', ['ngTable', 'ngResource'])
    .config(['$stateProvider',function($stateProvider) {
        $stateProvider
            .state('incidents-created',{
                url:'/created',
                templateUrl:'incident/incident.created.tpl.jade',
                data: {
                    pageTitle: '我创建的故障单'
                }
            })
        ;
    }])
    .factory('CreatedIncidentService', [
        '$resource', function($resource) {
            return $resource("/api/msu-incidents/created");
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
    .controller('CreatedIncidentListCtrl', [
        '$scope', '$location', '$timeout', 'ngTableParams', 'CreatedIncidentService', function($scope, $location, $timeout, ngTableParams, createdIncidentService) {
            var args, options;
            options = {
                page: 1,
                size: 10
            };
            args = {
                total: 0,
                getData: function($defer, params) {
                    $location.search(params.url());
                    return createdIncidentService.query(params.url(), function(data, headers) {
                        return $timeout(function() {
                            params.total(headers('total'));
                            return $defer.resolve($scope.incidents = data);
                        }, 500);
                    });
                }
            };
            $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args);
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
