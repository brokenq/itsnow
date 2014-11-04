angular.module('MsuIndex.IncidentDetail', ['ngTable', 'ngResource','Lib.Feedback'])
    .config(['$stateProvider',function($stateProvider) {
        $stateProvider
            .state('incidents-create',{
                url:'/incidents/create',
                templateUrl:'incident/incident.detail.tpl.jade',
                controller:'MsuCreateIncidentCtrl',
                data: {
                    pageTitle: '新建故障单'
                }
            })
            .state('incidents-action',{
                url:'/incidents/{id}/{action}',
                templateUrl:'incident/incident.detail.tpl.jade',
                controller:'MsuCreateIncidentCtrl',
                data: {
                    pageTitle: '处理故障单'
                }
            })
        ;
    }])
    // 新建故障工单
    .factory('MsuCreateIncidentService', ['$resource', function ($resource) {
        return $resource('/api/msu-incidents/start', {}, {
            start: {method: 'POST'}
        });
    }])
    // 查询故障单对应的当前任务信息
    .factory('MsuQueryIncidentTaskResource', ['$resource', function ($resource) {
        return $resource('/api/msu-incidents/:msuInstanceId', {msuInstanceId:'@msuInstanceId'}, {
            query: {
                method: 'GET'
            }
        });
    }])
    // 查询故障单对应的当前任务信息
    .factory('MsuQueryIncidentTaskService', ['MsuQueryIncidentTaskResource', function (msuQueryIncidentTaskResource) {
        return{
            query: function (msuInstanceId) {
                return msuQueryIncidentTaskResource.query({msuInstanceId: msuInstanceId}).$promise;
            }
        };
    }])
    // 签收/分析/处理/关闭故障工单
    .factory('MsuProcessIncidentService', ['$resource', function ($resource) {
        return $resource('/api/msu-incidents/:msuInstanceId/:taskId/complete', null, {
            complete: {method: 'PUT'}
        });
    }])
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
    .controller('MsuCreateIncidentCtrl', ['$rootScope','$scope','$stateParams', 'MsuCreateIncidentService','MsuQueryIncidentTaskService','MsuProcessIncidentService', '$location','IncidentModelService','Feedback',
    function ($rootScope,$scope,$stateParams, msuCreateIncidentService,msuQueryIncidentTaskService,msuProcessIncidentService, $location,incidentModelService,feedback) {

        $scope.selectedModel = incidentModelService.selectedModel;
        $scope.action = $stateParams.action;

        var taskId = '';
        var taskName = '';
        var taskAction = '';
        $scope.buttonLabel = '新建';
        var promise;

        if($rootScope.incident == null) {
            $scope.incident = {number: 'INC001', createdBy: 'admin'};
        }

        function getActionButton(status) {
            if(status == 'Assigned'){
                return '分配';
            }else if(status == 'Accepted'){
                return '签收';
            }else if(status == 'Resolving'){
                return '分析';
            }else if(status == 'Resolved'){
                return '解决';
            }else if(status == 'Closed'){
                return '关闭';
            }else{
                return '新建';
            }
        }

        if($scope.action == 'detail'){
            $scope.incident = $rootScope.incident;

            //查询任务信息
            promise = msuQueryIncidentTaskService.query($rootScope.incident.msuInstanceId);

            promise.then(function success(data) {
                for (var i in data.tasksList) {
                    if(angular.isDefined(data.tasksList[i].taskId)) {
                        taskId = data.tasksList[i].taskId;
                        taskName = data.tasksList[i].taskName;
                        taskAction = data.tasksList[i].taskDescription;
                        //alert('task id:'+taskId+' name:'+taskName+' Description:'+taskAction);
                        $scope.buttonLabel = getActionButton(taskAction);
                    }
                }
            }, function error(msg) {
                feedback.error('出错：'+msg);
            });
        }

        $scope.process = function () {
            if($scope.action == 'detail') {
                msuProcessIncidentService.complete({msuInstanceId:$scope.incident.msuInstanceId,taskId:taskId}, $scope.incident,function (data) {
                    if(data.result==='success'){
                        feedback.success($scope.buttonLabel+"成功");
                        $location.path('/incidents/opened');
                    }
                });
            }else {
                msuCreateIncidentService.start($scope.incident, function () {
                    feedback.success($scope.buttonLabel+"成功");
                    $location.path('/incidents/created');
                });
            }
        };

    }])
;
