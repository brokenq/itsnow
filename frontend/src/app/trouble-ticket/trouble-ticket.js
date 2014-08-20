/**
 * Created by Sin on 2014/8/6.
 */
// incident实体类
// 故障单Module

angular.module('ItsNow.TroubleTicket', ['ItsNow.TroubleTicket.Model','ItsNow.TroubleTicket.Service'])

    // 新建故障单
    .controller('NewTroubleTicketCtrl', ['$scope', 'NewTroubleTicketService', '$location', 'TroubleTicketModelSerivce',
        function ($scope, NewTroubleTicketService, $location, TroubleTicketModelSerivce) {

            $scope.selectedModel = TroubleTicketModelSerivce.selectedModel;

            $scope.save = function () {
                NewTroubleTicketService.start($scope.incident, function () {
                    $location.path('/index/list-trouble-ticket');
                });
            };

        }])

    // 签收故障单
    .controller('AcceptTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'QueryTroubleTicketTaskService', 'AcceptTroubleTicketService', 'TroubleTicketModelSerivce',
        function ($q, $rootScope, $scope, $location, QueryTroubleTicketTaskService, AcceptTroubleTicketService, TroubleTicketModelSerivce) {

            $scope.incident = $rootScope.incident;

            $scope.selectedModel = TroubleTicketModelSerivce.selectedModel;

            $scope.accept = function () {

                var taskId = '';

                var promise = QueryTroubleTicketTaskService.query($scope.incident.msuInstanceId);

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
                    .then(function(){
                        AcceptTroubleTicketService.complete({taskId:taskId}, $scope.incident,function (data) {
                            if(data.result==='true'){
                                alert("签收成功");
                                $location.path('/index/list-trouble-ticket');
                            };
                        });
                    }
                );
            };
        }])

    // 分析故障单
    .controller('AnalysisTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'QueryTroubleTicketTaskService', 'AnalysisTroubleTicketService', 'TroubleTicketModelSerivce',
        function ($q, $rootScope, $scope, $location, QueryTroubleTicketTaskService, AnalysisTroubleTicketService, TroubleTicketModelSerivce) {

            $scope.incident = $rootScope.incident;

            $scope.selectedModel = TroubleTicketModelSerivce.selectedModel;

            $scope.analysis = function () {

                var taskId = '';

                var promise = QueryTroubleTicketTaskService.query($scope.incident.msuInstanceId);

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
                    .then(function(){
                        AnalysisTroubleTicketService.complete({taskId:taskId,flag:true}, $scope.incident,function (data) {
                            if(data.result==='true'){
                                alert("分析完毕");
                                $location.path('/index/list-trouble-ticket');
                            };
                        });
                    }
                );
            };
        }])

    // 处理故障单
    .controller('ProcessTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'QueryTroubleTicketTaskService', 'ProcessTroubleTicketService', 'TroubleTicketModelSerivce',
        function ($q, $rootScope, $scope, $location, QueryTroubleTicketTaskService, ProcessTroubleTicketService, TroubleTicketModelSerivce) {

            $scope.incident = $rootScope.incident;

            $scope.selectedModel = TroubleTicketModelSerivce.selectedModel;

            $scope.process = function () {

                var taskId = '';

                var promise = QueryTroubleTicketTaskService.query($scope.incident.msuInstanceId);

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
                    .then(function(){
                        ProcessTroubleTicketService.complete({taskId:taskId,resolved:true}, $scope.incident,function (data) {
                            if(data.result==='true'){
                                alert("处理完毕");
                                $location.path('/index/list-trouble-ticket');
                            };
                        });
                    }
                );
            };
        }])

    // 关闭故障单
    .controller('CloseTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'QueryTroubleTicketTaskService', 'CloseTroubleTicketService', 'TroubleTicketModelSerivce',
        function ($q, $rootScope, $scope, $location, QueryTroubleTicketTaskService, CloseTroubleTicketService, TroubleTicketModelSerivce) {

            $scope.incident = $rootScope.incident;

            $scope.selectedModel = TroubleTicketModelSerivce.selectedModel;

            $scope.close = function () {

                var taskId = '';

                var promise = QueryTroubleTicketTaskService.query($scope.incident.msuInstanceId);

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
                    .then(function(){
                        CloseTroubleTicketService.complete({taskId:taskId}, $scope.incident,function (data) {
                            if(data.result==='true'){
                                alert("关闭成功");
                                $location.path('/index/list-trouble-ticket');
                            };
                        });
                    }
                );
            };
        }])

    // 故障单列表控制器
    .controller('ListTroubleTicketCtrl', ['$q','$rootScope', '$scope', 'ListTroubleTicketService', 'QueryTroubleTicketTaskService', 'SearchTroubleTicketService', '$location',
        function ($q, $rootScope, $scope, ListTroubleTicketService, QueryTroubleTicketTaskService, SearchTroubleTicketService, $location) {

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
                {field: 'msuStatus', displayName: '状态'},
                {field: 'mspAccountName', displayName: 'MSP帐户'},
                {field: 'mspStatus', displayName: 'MSP状态'}
            ]
        };

            $scope.searchFun = function (){
                $scope.getPagedDataAsyncBySearch($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.searchKey);
            };

            $scope.newFun = function (){
                $location.path('/index/new-trouble-ticket');
            };

            $scope.acceptFun = function (){

                var promise;
                var taskName = '';

                for(var ticket in $scope.mySelections){
                    $rootScope.incident = $scope.mySelections[ticket];
                    promise = QueryTroubleTicketTaskService.query($rootScope.incident.msuInstanceId);
                };

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        taskName = data.tasks[i].taskName;
                    }
                    if(taskName==='firstline accept_incident'){
                        $location.path('/index/accept-trouble-ticket');
                    }else{
                        alert("流程错误！下一步处理应为："+taskName);
                    };
                }, function error(msg) {
                    alert('出错：'+msg);
                });
            };

            $scope.analysisFun = function (){

                var promise;
                var taskName = '';

                for(var ticket in $scope.mySelections){
                    $rootScope.incident = $scope.mySelections[ticket];
                    promise = QueryTroubleTicketTaskService.query($rootScope.incident.msuInstanceId);
                };

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        var task = data.tasks[i];
                        taskName = task.taskName;
                    }

                    if(taskName==='firstline analysis_incident'){
                        $location.path('/index/analysis-trouble-ticket');
                    }else{
                        alert("流程错误！下一步处理应为："+taskName);
                    };

                }, function error(msg) {
                    console.error(msg);
                });
            };

            $scope.processFun = function (){

                var promise;
                var taskName = '';

                for(var ticket in $scope.mySelections){
                    $rootScope.incident = $scope.mySelections[ticket];
                    promise = QueryTroubleTicketTaskService.query($rootScope.incident.msuInstanceId);
                };

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        var task = data.tasks[i];
                        taskName = task.taskName;
                    }

                    if(taskName==='firstline process_incident'){
                        $location.path('/index/process-trouble-ticket');
                    }else{
                        alert("流程错误！下一步处理应为："+taskName);
                    };

                }, function error(msg) {
                    console.error(msg);
                });
            };

            $scope.closeFun = function (){

                var promise;
                var taskName = '';

                for(var ticket in $scope.mySelections){
                    $rootScope.incident = $scope.mySelections[ticket];
                    promise = QueryTroubleTicketTaskService.query($rootScope.incident.msuInstanceId);
                };

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        var task = data.tasks[i];
                        taskName = task.taskName;
                    }

                    if(taskName.indexOf('close incident')>-1){
                        $location.path('/index/close-trouble-ticket');
                    }else{
                        alert("流程错误！下一步处理应为："+taskName);
                    };

                }, function error(msg) {
                    console.error(msg);
                });
            };
        }])
;
