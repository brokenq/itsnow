/**
 * Created by Sin on 2014/8/6.
 */
// incident实体类
// 故障单Module

angular.module('ItsNow.TroubleTicket', [])

    .controller('TroubleTicketCtrl', ['$rootScope', '$scope', 'NewTroubleTicketService', '$location',
        function ($rootScope, $scope, NewTroubleTicketService, $location) {

            $scope.requesterTypeModule = [
                {id: '001', name: 'email', value: 1},
                {id: '002', name: 'phone', value: 2},
                {id: '003', name: 'web', value: 3}
            ];

            $scope.areaModule = [
                {id: '001', name: '北京', value: 1},
                {id: '002', name: '上海', value: 2},
                {id: '003', name: '天津', value: 3},
                {id: '004', name: '重庆', value: 4}
            ];

            $scope.serviceCatalogModule = [
                {id: '001', name: '天', value: 1},
                {id: '002', name: '地', value: 2},
                {id: '003', name: '人', value: 3},
                {id: '004', name: '畜', value: 4}
            ];

            $scope.impactModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.categoryModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.urgencyModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.ciModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.priorityModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.save = function () {
                NewTroubleTicketService.start($scope.incident, function () {
                    $location.path('/index/list-trouble-ticket');
                });
            };

        }])

    .controller('AcceptTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'QueryTroubleTicketTaskService', 'AcceptTroubleTicketService',
        function ($q, $rootScope, $scope, $location, QueryTroubleTicketTaskService, AcceptTroubleTicketService) {

            $scope.acceptIncident = $rootScope.acceptIncident;

            $scope.requesterTypeModule = [
                {id: '001', name: 'email', value: 1},
                {id: '002', name: 'phone', value: 2},
                {id: '003', name: 'web', value: 3}
            ];

            $scope.areaModule = [
                {id: '001', name: '北京', value: 1},
                {id: '002', name: '上海', value: 2},
                {id: '003', name: '天津', value: 3},
                {id: '004', name: '重庆', value: 4}
            ];

            $scope.serviceCatalogModule = [
                {id: '001', name: '天', value: 1},
                {id: '002', name: '地', value: 2},
                {id: '003', name: '人', value: 3},
                {id: '004', name: '畜', value: 4}
            ];

            $scope.impactModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.categoryModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.urgencyModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.ciModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.priorityModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.accept = function () {

                var taskId = '';

                var promise = QueryTroubleTicketTaskService.query($scope.acceptIncident.instanceId);

                promise
                    .then(function (data) {
                    for(var task in data.tasks){
                        var t = data.tasks[task];
                        taskId = t.taskId;
                    }
                    var anotherDeferred = $q.defer();
                    anotherDeferred.resolve(taskId);
                    return anotherDeferred.promise;
                })
                    .then(function(data){
                        AcceptTroubleTicketService.complete({taskId:taskId}, $scope.acceptIncident,function (data) {
                            if(data.result==='true'){
                                alert("签收成功");
                                $location.path('/index/list-trouble-ticket');
                            };
                        });
                    }
                );
            };
        }])

    .controller('AnalysisTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'QueryTroubleTicketTaskService', 'AnalysisTroubleTicketService',
        function ($q, $rootScope, $scope, $location, QueryTroubleTicketTaskService, AnalysisTroubleTicketService) {

            $scope.analysisIncident = $rootScope.analysisIncident;

            $scope.requesterTypeModule = [
                {id: '001', name: 'email', value: 1},
                {id: '002', name: 'phone', value: 2},
                {id: '003', name: 'web', value: 3}
            ];

            $scope.areaModule = [
                {id: '001', name: '北京', value: 1},
                {id: '002', name: '上海', value: 2},
                {id: '003', name: '天津', value: 3},
                {id: '004', name: '重庆', value: 4}
            ];

            $scope.serviceCatalogModule = [
                {id: '001', name: '天', value: 1},
                {id: '002', name: '地', value: 2},
                {id: '003', name: '人', value: 3},
                {id: '004', name: '畜', value: 4}
            ];

            $scope.impactModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.categoryModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.urgencyModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.ciModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.priorityModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.analysis = function () {

                var taskId = '';

                var promise = QueryTroubleTicketTaskService.query($scope.analysisIncident.instanceId);

                promise
                    .then(function (data) {
                    for(var task in data.tasks){
                        var t = data.tasks[task];
                        taskId = t.taskId;
                    }
                    var anotherDeferred = $q.defer();
                    anotherDeferred.resolve(taskId);
                    return anotherDeferred.promise;
                })
                    .then(function(data){
                        AnalysisTroubleTicketService.complete({taskId:taskId,flag:true}, $scope.analysisIncident,function (data) {
                            if(data.result==='true'){
                                alert("分析完毕");
                                $location.path('/index/list-trouble-ticket');
                            };
                        });
                    }
                );
            };
        }])

    .controller('ProcessTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'QueryTroubleTicketTaskService', 'ProcessTroubleTicketService',
        function ($q, $rootScope, $scope, $location, QueryTroubleTicketTaskService, ProcessTroubleTicketService) {

            $scope.processIncident = $rootScope.processIncident;

            $scope.requesterTypeModule = [
                {id: '001', name: 'email', value: 1},
                {id: '002', name: 'phone', value: 2},
                {id: '003', name: 'web', value: 3}
            ];

            $scope.areaModule = [
                {id: '001', name: '北京', value: 1},
                {id: '002', name: '上海', value: 2},
                {id: '003', name: '天津', value: 3},
                {id: '004', name: '重庆', value: 4}
            ];

            $scope.serviceCatalogModule = [
                {id: '001', name: '天', value: 1},
                {id: '002', name: '地', value: 2},
                {id: '003', name: '人', value: 3},
                {id: '004', name: '畜', value: 4}
            ];

            $scope.impactModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.categoryModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.urgencyModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.ciModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.priorityModule = [
                {id: '001', name: '高', value: 1},
                {id: '002', name: '中', value: 2},
                {id: '003', name: '低', value: 3}
            ];

            $scope.process = function () {

                var taskId = '';

                var promise = QueryTroubleTicketTaskService.query($scope.processIncident.instanceId);

                promise
                    .then(function (data) {
                    for(var task in data.tasks){
                        var t = data.tasks[task];
                        taskId = t.taskId;
                    }
                    var anotherDeferred = $q.defer();
                    anotherDeferred.resolve(taskId);
                    return anotherDeferred.promise;
                })
                    .then(function(data){
                        ProcessTroubleTicketService.complete({taskId:taskId,resolved:true}, $scope.processIncident,function (data) {
                            if(data.result==='true'){
                                alert("处理完毕");
                                $location.path('/index/list-trouble-ticket');
                            };
                        });
                    }
                );
            };
        }])

    .factory('NewTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/incidents/start', {}, {
            start: {method: 'POST'}
        });
    }])

    .factory('AcceptTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/incidents/:taskId/complete', null, {
            complete: {method: 'PUT'}
        });
    }])

    .factory('AnalysisTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/incidents/:taskId/complete?can_process=:flag', null, {
            complete: {method: 'PUT'}
        });
    }])

    .factory('ProcessTroubleTicketService', ['$resource', function ($resource) {
        //http://localhost:8071/api/incidents/133/complete?resolved=true&_csrf=7f8daae9-eabb-4f27-a392-396f90a446b3
        return $resource('/api/incidents/:taskId/complete?:flag', null, {
            complete: {method: 'PUT'}
        });
    }])

    .factory('CloseTroubleTicketService', ['$resource', function ($resource) {
        //http://localhost:8071/api/incidents/141/complete?_csrf=67e36a39-f16a-439e-acc2-b2c1f23b40f8
        return $resource('/api/incidents/:taskId/complete', null, {
            complete: {method: 'PUT'}
        });
    }])

    // 故障单列表控制器
    .controller('ListTroubleTicketCtrl', ['$q','$rootScope', '$scope', 'ListTroubleTicketService', 'QueryTroubleTicketTaskService', 'SearchTroubleTicketService', '$location', 'CloseTroubleTicketService',
        function ($q, $rootScope, $scope, ListTroubleTicketService, QueryTroubleTicketTaskService, SearchTroubleTicketService, $location, CloseTroubleTicketService) {

        $scope.mySelections = [];
        $scope.filterOptions = {
            filterText: "",
            useExternalFilter: true
        };
        $scope.totalServerItems = 0;
        $scope.pagingOptions = {
            pageSizes: [20],
            pageSize: 20,
            currentPage: 1
        };

        $scope.searchFun = function (){
            $scope.getPagedDataAsyncBySearch($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.searchKey);
        };

        $scope.acceptFun = function (){

            var instanceId = '';
            var taskName = '';
            var promise;

            for(var ticket in $scope.mySelections){
                instanceId = $scope.mySelections[ticket].instanceId;
                promise = QueryTroubleTicketTaskService.query(instanceId);
            };

            promise.then(function success(data) {
                for (var i in data.tasks) {
                    var task = data.tasks[i];
                    taskName = task.taskName;
                }

                if(taskName==='firstline accept_incident'){
                    var deferred1 = $q.defer();
                    var promise1 = deferred1.promise;
                    SearchTroubleTicketService.query({key:instanceId},
                        function(data){
                            for(var i in data){
                                var troubleTicket = data[i];
                                if(troubleTicket.instanceId===instanceId){
                                    $rootScope.acceptIncident = troubleTicket;
                                }
                            }
                            deferred1.resolve($rootScope.acceptIncident);
                        }
                    );
                    promise1.then(function(){
                        $location.path('/index/accept-trouble-ticket');
                    })
                }else{
                    alert("流程错误！下一步处理应为："+taskName);
                };

            }, function error(msg) {
                alert('出错：'+msg);
            });

        };

        $scope.analysisFun = function (){

            var instanceId = '';
            var taskName = '';
            var promise;

            for(var ticket in $scope.mySelections){
                instanceId = $scope.mySelections[ticket].instanceId;
                promise = QueryTroubleTicketTaskService.query(instanceId);
            };

            promise.then(function success(data) {
                for (var i in data.tasks) {
                    var task = data.tasks[i];
                    taskName = task.taskName;
                }

                if(taskName==='firstline analysis_incident'){
                    var deferred1 = $q.defer();
                    var promise1 = deferred1.promise;
                    SearchTroubleTicketService.query({key:instanceId},
                        function(data){
                            for(var i in data){
                                var troubleTicket = data[i];
                                if(troubleTicket.instanceId===instanceId){
                                    $rootScope.analysisIncident = troubleTicket;
                                }
                            }
                            deferred1.resolve($rootScope.analysisIncident);
                        }
                    );
                    promise1.then(function(){
                        $location.path('/index/analysis-trouble-ticket');
                    })
                }else{
                    alert("流程错误！下一步处理应为："+taskName);
                };

            }, function error(msg) {
                console.error(msg);
            });

        };

        $scope.processFun = function (){

                var instanceId = '';
                var taskName = '';
                var promise;

                for(var ticket in $scope.mySelections){
                    instanceId = $scope.mySelections[ticket].instanceId;
                    promise = QueryTroubleTicketTaskService.query(instanceId);
                };

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        var task = data.tasks[i];
                        taskName = task.taskName;
                    }

                    if(taskName==='firstline process_incident'){
                        var deferred1 = $q.defer();
                        var promise1 = deferred1.promise;
                        SearchTroubleTicketService.query({key:instanceId},
                            function(data){
                                for(var i in data){
                                    var troubleTicket = data[i];
                                    if(troubleTicket.instanceId===instanceId){
                                        $rootScope.processIncident = troubleTicket;
                                    }
                                }
                                deferred1.resolve($rootScope.processIncident);
                            }
                        );
                        promise1.then(function(){
                            $location.path('/index/process-trouble-ticket');
                        })
                    }else{
                        alert("流程错误！下一步处理应为："+taskName);
                    };

                }, function error(msg) {
                    console.error(msg);
                });

        };

        $scope.closeFun = function (){

                var instanceId = '';
                var taskId = '';
                var taskName = '';
                var promise;

                for(var ticket in $scope.mySelections){
                    instanceId = $scope.mySelections[ticket].instanceId;
                    promise = QueryTroubleTicketTaskService.query(instanceId);
                };

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        var task = data.tasks[i];
                        taskId = task.taskId;
                        taskName = task.taskName;
                    }

                    if(taskName==='servicedesk close incident'){
                        var deferred1 = $q.defer();
                        var promise1 = deferred1.promise;
                        SearchTroubleTicketService.query({key:instanceId},
                            function(data){
                                for(var i in data){
                                    var troubleTicket = data[i];
                                    if(troubleTicket.instanceId===instanceId){
                                        $scope.processIncident = troubleTicket;
                                    }
                                }
                                deferred1.resolve($scope.processIncident);
                            }
                        );
                        promise1.then(function(){
                            CloseTroubleTicketService.complete({taskId:taskId}, $scope.processIncident,function (data) {
                                if(data.result==='true'){
                                    alert('关闭成功');
                                    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
                                };
                            });
                        })
                    }else{
                        alert("流程错误！下一步处理应为："+taskName);
                    };

                }, function error(msg) {
                    console.error(msg);
                });

        };

        $scope.setPagingData = function (data, page, pageSize) {
            var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
            $scope.troubleTicketList = pagedData;
            $scope.totalServerItems = data.length;
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        };

        $scope.getPagedDataAsync = function (pageSize, page, searchText) {
            setTimeout(function () {
                var data;
                if (searchText) {
                    var ft = searchText.toLowerCase();
                    ListTroubleTicketService.list(function (largeLoad) {
                        data = largeLoad.filter(function (item) {
                            return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                        });
                        $scope.setPagingData(data, page, pageSize);
                    });
                } else {
                    ListTroubleTicketService.list(function (largeLoad) {
                        $scope.setPagingData(largeLoad, page, pageSize);
                    });
                }
            }, 100);
        };

        $scope.getPagedDataAsyncBySearch = function (pageSize, page, searchText) {
            setTimeout(function () {
                SearchTroubleTicketService.query({key:searchText}, function (largeLoad) {
                    $scope.setPagingData(largeLoad, page, pageSize);
                });
            }, 100);
        };

        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

        $scope.$watch('pagingOptions', function (newVal, oldVal) {
            if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
            }
        }, true);
        $scope.$watch('filterOptions', function (newVal, oldVal) {
            if (newVal !== oldVal) {
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
            }
        }, true);

        // 故障单列表
        $scope.gridUserList = {
            data: 'troubleTicketList',
            enablePaging: true,
            showFooter: true,
            totalServerItems: 'totalServerItems',
            pagingOptions: $scope.pagingOptions,
            filterOptions: $scope.filterOptions,
            multiSelect: false,
            showSelectionCheckbox: true,
            selectedItems: $scope.mySelections,
            columnDefs: [
                {field: 'number', displayName: '故障单号'},
                {field: 'requesterName', displayName: '请求人'},
                {field: 'requestDescription', displayName: '故障描述'},
                {field: 'serviceCatalog', displayName: '服务目录'},
                {field: 'priority', displayName: '优先级'},
                {field: 'status', displayName: '状态'},
                {field: 'assignedGroup', displayName: '分配组'},
                {field: 'assignedUser', displayName: '分配用户'}
            ]
        };
    }])

    .factory('ListTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/incidents/?', {}, {
            list: {
                method: 'GET',
                isArray: true
            }
        });
    }])

    // 根据关键字搜索故障单信息
    .factory('SearchTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/incidents/?key=:key', {key:'@key'}, {
            query: {
                method: 'GET',
                isArray: true
            }
        });
    }])

    // 查询故障单对应的当前任务信息
    .factory('QueryTroubleTicketTaskResource', ['$resource', function ($resource) {
        return $resource('/api/incidents/:instanceId', {instanceId:'@instanceId'}, {
            query: {
                method: 'GET'
            }
        });
    }])
    // 查询故障单对应的当前任务信息
    .factory('QueryTroubleTicketTaskService', ['QueryTroubleTicketTaskResource', function (QueryTroubleTicketTaskResource) {
        return{
            query: function (instanceId) {
                return QueryTroubleTicketTaskResource.query({instanceId: instanceId}).$promise;
            }
        };
    }])
;
