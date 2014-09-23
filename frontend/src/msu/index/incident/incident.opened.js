angular.module('MsuIndex.IncidentOpened', ['ngTable', 'ngResource'])
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
            return $resource("/api/msu-incidents");
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
                    {name: 'Hardware'},
                    {name: 'Software'},
                    {name: 'Consult'},
                    {name: 'Others'}
                ],
                impactModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                categoryModule : [
                    {name: '硬件'},
                    {name: '软件'},
                    {name: '数据库'}
                ],
                urgencyModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                ciModule : [
                    {name: 'hp-rp-5470-1'},
                    {name: 'ibm-p550-1'},
                    {name: 'dell-001'}
                ],
                priorityModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                statusModule : [
                    {id:'1',name: 'New',action:'新建',status:'已新建'},
                    {id:'2',name: 'Assigned',action:'分派',status:'已分派'},
                    {id:'3',name: 'Accepted',action:'签收',status:'已签收'},
                    {id:'4',name: 'Resolving',action:'分析',status:'处理中'},
                    {id:'5',name: 'Resolved',action:'处理',status:'已解决'},
                    {id:'6',name: 'Closed',action:'关闭',status:'已关闭'}
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
