/**
 * Created by Sin on 2014/8/6.
 */
// incident实体类
// 故障单Module

angular.module('MspIndex.Incident', [
  'MspIndex.Incident.Model',
  'MspIndex.Incident.Service'
])

    // 新建故障单
    .controller('MSPNewTroubleTicketCtrl', ['$scope', 'MSPNewTroubleTicketService', '$location', 'MSPTroubleTicketModelService',
        function ($scope, MSPNewTroubleTicketService, $location, MSPTroubleTicketModelService) {

            $scope.selectedModel = MSPTroubleTicketModelService.selectedModel;

            $scope.save = function () {
                MSPNewTroubleTicketService.start($scope.incident, function () {
                    $location.path('/index/list-trouble-ticket');
                });
            };

        }])

    // 签收故障单
    .controller('MSPAcceptTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'MSPQueryTroubleTicketTaskService', 'MSPAcceptTroubleTicketService', 'MSPTroubleTicketModelService',
        function ($q, $rootScope, $scope, $location, MSPQueryTroubleTicketTaskService, MSPAcceptTroubleTicketService, MSPTroubleTicketModelService) {

            $scope.incident = $rootScope.incident;

            $scope.selectedModel = MSPTroubleTicketModelService.selectedModel;

            $scope.accept = function () {

                var taskId = '';

                var promise = MSPQueryTroubleTicketTaskService.query($scope.incident.mspInstanceId);

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
                        MSPAcceptTroubleTicketService.complete({taskId:taskId}, $scope.incident,function (data) {
                            if(data.result==='true'){
                                alert("签收成功");
                                $location.path('/index/list-trouble-ticket');
                            }
                        });
                    }
                );
            };
        }])

    // 分析故障单
    .controller('MSPAnalysisTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'MSPQueryTroubleTicketTaskService', 'MSPAnalysisTroubleTicketService', 'MSPTroubleTicketModelService',
        function ($q, $rootScope, $scope, $location, MSPQueryTroubleTicketTaskService, MSPAnalysisTroubleTicketService, MSPTroubleTicketModelService) {

            $scope.incident = $rootScope.incident;

            $scope.selectedModel = MSPTroubleTicketModelService.selectedModel;

            $scope.analysis = function () {

                var taskId = '';

                var promise = MSPQueryTroubleTicketTaskService.query($scope.incident.mspInstanceId);

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
                        MSPAnalysisTroubleTicketService.complete({taskId:taskId,flag:true}, $scope.incident,function (data) {
                            if(data.result==='true'){
                                alert("分析完毕");
                                $location.path('/index/list-trouble-ticket');
                            }
                        });
                    }
                );
            };
        }])

    // 处理故障单
    .controller('MSPProcessTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'MSPQueryTroubleTicketTaskService', 'MSPProcessTroubleTicketService', 'MSPTroubleTicketModelService',
        function ($q, $rootScope, $scope, $location, MSPQueryTroubleTicketTaskService, MSPProcessTroubleTicketService, MSPTroubleTicketModelService) {

            $scope.incident = $rootScope.incident;

            $scope.selectedModel = MSPTroubleTicketModelService.selectedModel;

            $scope.process = function () {

                var taskId = '';

                var promise = MSPQueryTroubleTicketTaskService.query($scope.incident.mspInstanceId);

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
                        MSPProcessTroubleTicketService.complete({taskId:taskId,resolved:true}, $scope.incident,function (data) {
                            if(data.result==='true'){
                                alert("处理完毕");
                                $location.path('/index/list-trouble-ticket');
                            }
                        });
                    }
                );
            };
        }])

    // 关闭故障单
    .controller('MSPCloseTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'MSPQueryTroubleTicketTaskService', 'MSPCloseTroubleTicketService', 'MSPTroubleTicketModelService',
        function ($q, $rootScope, $scope, $location, MSPQueryTroubleTicketTaskService, MSPCloseTroubleTicketService, MSPTroubleTicketModelService) {

            $scope.incident = $rootScope.incident;

            $scope.selectedModel = MSPTroubleTicketModelService.selectedModel;

            $scope.close = function () {

                var taskId = '';

                var promise = MSPQueryTroubleTicketTaskService.query($scope.incident.mspInstanceId);

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
                        MSPCloseTroubleTicketService.complete({taskId:taskId}, $scope.incident,function (data) {
                            if(data.result==='true'){
                                alert("关闭成功");
                                $location.path('/index/list-trouble-ticket');
                            }
                        });
                    }
                );
            };
        }])

    // 故障单列表控制器
    .controller('MSPListTroubleTicketCtrl', ['$q','$rootScope', '$scope', 'MSPListTroubleTicketService', 'MSPQueryTroubleTicketTaskService', 'MSPSearchTroubleTicketService', '$location',
        function ($q, $rootScope, $scope, MSPListTroubleTicketService, MSPQueryTroubleTicketTaskService, MSPSearchTroubleTicketService, $location) {

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
                    MSPListTroubleTicketService.list(function (largeLoad) {
                        data = largeLoad.filter(function (item) {
                            return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                        });
                        $scope.setPagingData(data, page, pageSize);
                    });
                } else {
                    MSPListTroubleTicketService.list(function (largeLoad) {
                        $scope.setPagingData(largeLoad, page, pageSize);
                    });
                }
            }, 100);
        };

        $scope.getPagedDataAsyncBySearch = function (pageSize, page, searchText) {
            setTimeout(function () {
                MSPSearchTroubleTicketService.query({key:searchText}, function (largeLoad) {
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
                {field: 'msuAccountName', displayName: '帐户'},
                {field: 'msuStatus', displayName: 'MSU状态'},
                {field: 'number', displayName: '故障单号'},
                {field: 'requesterName', displayName: '请求人'},
                {field: 'requestDescription', displayName: '故障描述'},
                {field: 'serviceCatalog', displayName: '服务目录'},
                {field: 'priority', displayName: '优先级'},
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
                    promise = MSPQueryTroubleTicketTaskService.query($rootScope.incident.mspInstanceId);
                }

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        taskName = data.tasks[i].taskName;
                    }
                    if(taskName.indexOf('accept')>-1){
                        $location.path('/index/accept-trouble-ticket');
                    }else{
                        alert("流程错误！下一步处理应为："+taskName);
                    }
                }, function error(msg) {
                    alert('出错：'+msg);
                });
            };

            $scope.analysisFun = function (){

                var promise;
                var taskName = '';

                for(var ticket in $scope.mySelections){
                    $rootScope.incident = $scope.mySelections[ticket];
                    promise = MSPQueryTroubleTicketTaskService.query($rootScope.incident.mspInstanceId);
                }

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        var task = data.tasks[i];
                        taskName = task.taskName;
                    }
                    if(taskName.indexOf('analysis')>-1){
//                    if(taskName==='firstline analysis_incident'){
                        $location.path('/index/analysis-trouble-ticket');
                    }else{
                        alert("流程错误！下一步处理应为："+taskName);
                    }

                }, function error(msg) {
                    console.error(msg);
                });
            };

            $scope.processFun = function (){

                var promise;
                var taskName = '';

                for(var ticket in $scope.mySelections){
                    $rootScope.incident = $scope.mySelections[ticket];
                    promise = MSPQueryTroubleTicketTaskService.query($rootScope.incident.mspInstanceId);
                }

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        var task = data.tasks[i];
                        taskName = task.taskName;
                    }
                    if(taskName.indexOf('process')>-1){
//                    if(taskName==='firstline process_incident'){
                        $location.path('/index/process-trouble-ticket');
                    }else{
                        alert("流程错误！下一步处理应为："+taskName);
                    }

                }, function error(msg) {
                    console.error(msg);
                });
            };

            $scope.closeFun = function (){

                var promise;
                var taskName = '';

                for(var ticket in $scope.mySelections){
                    $rootScope.incident = $scope.mySelections[ticket];
                    promise = MSPQueryTroubleTicketTaskService.query($rootScope.incident.mspInstanceId);
                }

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        var task = data.tasks[i];
                        taskName = task.taskName;
                    }
                    if(taskName.indexOf('close')>-1){
//                    if(taskName.indexOf('close incident')>-1){
                        $location.path('/index/close-trouble-ticket');
                    }else{
                        alert("流程错误！下一步处理应为："+taskName);
                    }

                }, function error(msg) {
                    console.error(msg);
                });
            };
        }])
;
