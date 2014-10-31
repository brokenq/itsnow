// List System
angular.module('System.WorkTimeDetail', ['ngTable', 'ngResource','Lib.Feedback'])

    .config(function ($stateProvider) {
        $stateProvider.state('worktime_new', {
            url: '/worktime_new',
            templateUrl: 'system/work_time/detail.tpl.jade',
            data: {pageTitle: '新建工作时间'}
        });
        $stateProvider.state('worktime_edit',{
            url:'/worktime_edit/{sn}',
            templateUrl:'system/work_time/detail.tpl.jade',
            data:{pageTitle:'工作时间修改'}
        });
    })
    .controller('WorkTimeNewCtrl', ['$rootScope','$scope','$location','$timeout','$state','$stateParams','WorkTimeService','Feedback',
        function ($rootScope,$scope, $location, $timeout,$state,$stateParams,WorkTimeService,Feedback) {
            var sn=$stateParams.sn;
          //  初始化数据
            $scope.workdates = [
                {   id: '1', name: '星期一'},  {   id: '2', name: '星期二'},  {   id: '3', name: '星期三'},
                {   id: '4', name: '星期四'},  {   id: '5', name: '星期五'},  {   id: '6', name: '星期六'},
                {   id: '7', name: '星期天'}
            ];
            $scope.selection =[];
            $scope.checkTime=function(){
                var stime=$scope.worktime.startAt.split(":").join("");
                var etime=$scope.worktime.endAt.split(":").join("");
                var stimenum=Number(stime);
                var etimenum=Number(etime);
                if(etimenum<stimenum){
                    $scope.worktime.endAt="";
                    Feedback.warn("结束时间要大于开始时间!!请重新设置");
                   // alert("结束时间要大于开始时间！！请重新设置！！");
                }
            };
            $scope.toggleSelection=function(workDateName){
                var idx=$scope.selection.indexOf(workDateName);
                if(idx>-1){
                    $scope.selection.splice(idx,1);

                }else{
                    $scope.selection.push(workDateName);

                }
            };
            // 判断是增加还是编辑入口
            if (sn !== null && sn !== "" && sn !== undefined) {
                WorkTimeService.get({sn:sn},function(data){
                    $scope.worktime=data;
                    $scope.selection=data.name.split(",");
                });
                $scope.changeWorkTime=function(){
                    $scope.worktime.name="";
                    var selections=$scope.selection;
                    for(var i=0;i<selections.length;i++){
                        if(i<$scope.selection.length-1){
                            $scope.worktime.name=selections[i]+','+$scope.worktime.name;
                        }else{
                            $scope.worktime.name=$scope.worktime.name+selections[i];
                        }

                    }
                    $scope.worktime.$promise = undefined;
                    $scope.worktime.$resolved = undefined;
                    WorkTimeService.update({sn:sn},$scope.worktime, function () {
                        $location.path('/worktime');
                    }, function (data) {
                        alert(data);
                    });
                };
            }else{
                $scope.changeWorkTime=function(){
                    $scope.worktime.name="";
                    var selections=$scope.selection;
                    for(var i=0;i<selections.length;i++){
                        if(i<$scope.selection.length-1){
                            $scope.worktime.name=selections[i]+','+$scope.worktime.name;
                        }else{
                            $scope.worktime.name=$scope.worktime.name+selections[i];
                        }

                    }
                    $scope.worktime.$promise = undefined;
                    $scope.worktime.$resolved = undefined;
                    WorkTimeService.save($scope.worktime,function(){
                        $location.path('/worktime');
                    });
                };
            }
        }
    ]);
