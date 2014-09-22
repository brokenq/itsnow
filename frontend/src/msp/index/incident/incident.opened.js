angular.module('MspIndex.IncidentOpened', ['ngTable', 'ngResource'])
    .config(['$stateProvider',function($stateProvider) {
        $stateProvider
            .state('incidents-opened',{
                url:'/opened',
                templateUrl:'incident/incident.opened.tpl.jade',
                controller:'IncidentListCtrl',
                data: {
                    pageTitle: '我的故障单'
                }
            })
        ;
    }])
    //获取我的故障单
    .factory('IncidentService', [
        '$resource', function($resource) {
            return $resource("/api/msp-incidents");
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

    .factory('IncidentModelService', function () {
        return {
            selectedModel : {
                requestTypeModule : [
                    {name: 'email'},
                    {name: 'phone'},
                    {name: 'web'}
                ],
                areaModule : [
                    {name: '北京'},
                    {name: '上海'},
                    {name: '天津'},
                    {name: '重庆'}
                ],
                serviceCatalogModule : [
                    {name: '天'},
                    {name: '地'},
                    {name: '人'},
                    {name: '畜'}
                ],
                impactModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                categoryModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                urgencyModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                ciModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                priorityModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                statusModule : [
                    {name: 'New'},
                    {name: 'Assigned'},
                    {name: 'Accepted'},
                    {name: 'Resolving'},
                    {name: 'Resolved'},
                    {name: 'Closed'}
                ],
                closeCodeModule : [
                    {name: '1'},
                    {name: '2'},
                    {name: '3'}
                ]
            }
        };

    })

    .controller('IncidentListCtrl', [
        '$rootScope','$scope','$state', '$location', '$timeout', 'ngTableParams', 'IncidentService', function($rootScope,$scope,$state, $location, $timeout, ngTableParams, incidentService) {
            var args, options;
            options = {
                page: 1,
                size: 10
            };
            args = {
                total: 0,
                getData: function($defer, params) {
                    $location.search(params.url());
                    return incidentService.query(params.url(), function(data, headers) {
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

            $scope.editId = -1;

            var found = false;
            $scope.setEditId =  function(pid) {
                angular.forEach($scope.incidents,function(item){
                    if(item.id == pid){
                        found = true;
                        $rootScope.incident = item;
                    }

                });
                $scope.editId = pid;
                if(found) {
                    //alert('incident number:' + $rootScope.incident.number);
                    //$location.path('/{{pid}}/detail');
                    $state.go('incidents-action', { id: pid, action: 'detail' });
                }
                else{
                    $state.go('incidents-create',{action:'create'});
                }
            }

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
    ]);
