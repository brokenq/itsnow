/**
 * Created by Sin on 2014/8/6.
 */
// incident实体类
// 故障单Module

angular.module('ItsNow.TroubleTicket', ['ItsNow.TroubleTicket.Model','ItsNow.TroubleTicket.Service','ItsNow.Component.Table', 'ItsNow.Component.Dialog'])

    // 新建故障单
    .controller('NewTroubleTicketCtrl', ['$scope', '$element', '$location', 'NewTroubleTicketService', 'TroubleTicketModelSerivce',
        function ($scope, $element, $location, NewTroubleTicketService, TroubleTicketModelSerivce) {

            $scope.selectedModel = TroubleTicketModelSerivce.selectedModel;

            $scope.save = function () {
                console.log(angular.element($element.find('button#save')));
                angular.element($element.find('button#save')).attr('disabled','disabled');
                NewTroubleTicketService.start($scope.incident, function success () {
                    $location.path('/index/list-trouble-ticket');
                },function error (data){
                    console.error('error:'+data);
//                    angular.element($element.find('button#save')).removeAttr('disabled');
                });
            };

        }])

    // 签收故障单
    .controller('AcceptTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'QueryTroubleTicketTaskService', 'AcceptTroubleTicketService', 'TroubleTicketModelSerivce', 'DialogService',
        function ($q, $rootScope, $scope, $location, QueryTroubleTicketTaskService, AcceptTroubleTicketService, TroubleTicketModelSerivce, DialogService) {

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
                                DialogService('notify','','签收成功');
                                $location.path('/index/list-trouble-ticket');
                            };
                        });
                    }
                );
            };
        }])

    // 分析故障单
    .controller('AnalysisTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'QueryTroubleTicketTaskService', 'AnalysisTroubleTicketService', 'TroubleTicketModelSerivce', 'DialogService',
        function ($q, $rootScope, $scope, $location, QueryTroubleTicketTaskService, AnalysisTroubleTicketService, TroubleTicketModelSerivce, DialogService) {

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
                                DialogService('notify','','分析完毕');
                                $location.path('/index/list-trouble-ticket');
                            };
                        });
                    }
                );
            };
        }])

    // 处理故障单
    .controller('ProcessTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'QueryTroubleTicketTaskService', 'ProcessTroubleTicketService', 'TroubleTicketModelSerivce', 'DialogService',
        function ($q, $rootScope, $scope, $location, QueryTroubleTicketTaskService, ProcessTroubleTicketService, TroubleTicketModelSerivce, DialogService) {

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
                                DialogService('notify','','处理完毕');
                                $location.path('/index/list-trouble-ticket');
                            };
                        });
                    }
                );
            };
        }])

    // 关闭故障单
    .controller('CloseTroubleTicketCtrl', ['$q', '$rootScope', '$scope', '$location', 'QueryTroubleTicketTaskService', 'CloseTroubleTicketService', 'TroubleTicketModelSerivce', 'DialogService',
        function ($q, $rootScope, $scope, $location, QueryTroubleTicketTaskService, CloseTroubleTicketService, TroubleTicketModelSerivce, DialogService) {

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
                                DialogService('notify','','关闭成功');
                                $location.path('/index/list-trouble-ticket');
                            };
                        });
                    }
                );
            };
        }])

    // 故障单列表控制器
    .controller('ListTroubleTicketCtrl', ['$scope', 'ListTroubleTicketService', 'QueryTroubleTicketTaskService', 'SearchTroubleTicketService', 'StatusChangeService',
        function ($scope, ListTroubleTicketService, QueryTroubleTicketTaskService, SearchTroubleTicketService, StatusChangeService) {

            // 搜索列表
            $scope.ListService = ListTroubleTicketService;

            // 搜索框
            $scope.SearchService = SearchTroubleTicketService;

            // 查询记录状态
            $scope.QueryTaskService = QueryTroubleTicketTaskService;

            // 要显示的列及列名
            $scope.columnDefs = [
                {field: 'number', displayName: '故障单号'},
                {field: 'requesterName', displayName: '请求人'},
                {field: 'requestDescription', displayName: '故障描述'},
                {field: 'serviceCatalog', displayName: '服务目录'},
                {field: 'priority', displayName: '优先级'},
//                {field: 'status', displayName: '状态',cellTemplate: '<div ng-class="{new : row.getProperty(col.field) === \'New\'}"><div class="ngCellText">{{row.getProperty(col.field)}}</div></div>'},
                {field: 'status', displayName: '状态',cellTemplate: StatusChangeService.do()},
                {field: 'assignedGroup', displayName: '分配组'},
                {field: 'assignedUser', displayName: '分配用户'},
                {field: 'msuStatus', displayName: '状态'},
                {field: 'mspAccountName', displayName: 'MSP帐户'},
                {field: 'mspStatus', displayName: 'MSP状态'}
            ];

            // 需要跳转的URL
            $scope.newUrl = '/index/new-trouble-ticket';
            $scope.acceptUrl = '/index/accept-trouble-ticket';
            $scope.analysisUrl = '/index/analysis-trouble-ticket';
            $scope.processUrl = '/index/process-trouble-ticket';
            $scope.closeUrl = '/index/close-trouble-ticket';

        }])
;
