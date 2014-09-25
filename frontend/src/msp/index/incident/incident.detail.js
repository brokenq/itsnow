angular.module('MspIndex.IncidentDetail', ['ngTable', 'ngResource'])
    .config(['$stateProvider',function($stateProvider) {
        $stateProvider
            .state('incidents-create',{
                url:'/create',
                templateUrl:'incident/incident.detail.tpl.html',
                controller:'MspCreateIncidentCtrl',
                data: {
                    pageTitle: '新建故障单'
                }
            })
            .state('incidents-action',{
                url:'/{id}/{action}',
                templateUrl:'incident/incident.detail.tpl.html',
                controller:'MspCreateIncidentCtrl',
                data: {
                    pageTitle: '处理故障单'
                }
            })
        ;
    }])
    // 新建故障工单
    .factory('MspCreateIncidentService', ['$resource', function ($resource) {
        return $resource('/api/msp-incidents/start', {}, {
            start: {method: 'POST'}
        });
    }])
    // 查询故障单对应的当前任务信息
    .factory('MspQueryIncidentTaskResource', ['$resource', function ($resource) {
        return $resource('/api/msp-incidents/:mspInstanceId', {mspInstanceId:'@mspInstanceId'}, {
            query: {
                method: 'GET'
            }
        });
    }])
    // 查询故障单对应的当前任务信息
    .factory('MspQueryIncidentTaskService', ['MspQueryIncidentTaskResource', function (mspQueryIncidentTaskResource) {
        return{
            query: function (mspInstanceId) {
                return mspQueryIncidentTaskResource.query({mspInstanceId: mspInstanceId}).$promise;
            }
        };
    }])
    // 签收/分析/处理/关闭故障工单
    .factory('MspProcessIncidentService', ['$resource', function ($resource) {
        return $resource('/api/msp-incidents/:mspInstanceId/:taskId/complete', null, {
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
    .controller('MspCreateIncidentCtrl', ['$rootScope','$scope','$stateParams', 'MspCreateIncidentService','MspQueryIncidentTaskService','MspProcessIncidentService', '$location','IncidentModelService',
    function ($rootScope,$scope,$stateParams, mspCreateIncidentService,mspQueryIncidentTaskService,mspProcessIncidentService, $location,incidentModelService) {

        $scope.selectedModel = incidentModelService.selectedModel;
        $scope.action = $stateParams.action;

        var taskId = '';
        var taskName = '';
        var taskAction = '';
        $scope.buttonLabel = '新建';
        var promise;

        if($rootScope.incident == null){
            $scope.incident = {number:'INC001',createdBy:'admin'};
        }

        function getActionButton(status) {
            if(status == 'Assigned'){
                return '分配';
            }
            else if(status == 'Accepted'){
                return '签收';
            }
            else if(status == 'Resolving'){
                return '分析';
            }
            else if(status == 'Resolved'){
                return '解决';
            }
            else if(status == 'Closed'){
                return '关闭';
            }
            else{
                return '新建';
            }
        }

        if($scope.action == 'detail'){
            $scope.incident = $rootScope.incident;

            //查询任务信息
            promise = mspQueryIncidentTaskService.query($rootScope.incident.mspInstanceId);

            promise.then(function success(data) {
                for (var i in data.tasksList) {
                    taskId = data.tasksList[i].taskId;
                    taskName = data.tasksList[i].taskName;
                    taskAction = data.tasksList[i].taskDescription;
                    //alert('task id:'+taskId+' name:'+taskName+' Description:'+taskAction);
                    $scope.buttonLabel = getActionButton(taskAction);

                }
            }, function error(msg) {
                alert('出错：'+msg);
            });
        }

        $scope.process = function () {
            if($scope.action == 'detail') {
                mspProcessIncidentService.complete({mspInstanceId:$scope.incident.mspInstanceId,taskId:taskId}, $scope.incident,function (data) {
                    if(data.result==='success'){
                        alert($scope.buttonLabel+"成功");
                        $location.path('/opened');
                    }
                });
            }else {
                mspCreateIncidentService.start($scope.incident, function () {
                    $location.path('/opened');
                });
            }
        };

    }])
;
