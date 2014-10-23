// List System
angular.module('System.WorkTimeDetail', ['ngTable', 'ngResource'])

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
    .controller('WorkTimeNewCtrl', ['$rootScope','$scope','$location','$timeout','$state','$stateParams','WorkTimeService',
        function ($rootScope,$scope, $location, $timeout,$state,$stateParams,WorkTimeService) {
            var sn=$stateParams.sn;
          //  $scope.worktime=[];
            $scope.workdates = [
                {   id: '1', name: '星期一'},  {   id: '2', name: '星期二'},  {   id: '3', name: '星期三'},
                {   id: '4', name: '星期四'},  {   id: '5', name: '星期五'},  {   id: '6', name: '星期六'},
                {   id: '7', name: '星期天'}
            ];
            $scope.selection =[];


            $scope.toggleSelection=function(workDateName){
                var idx=$scope.selection.indexOf(workDateName);
                if(idx>-1){
                    $scope.selection.splice(idx,1);

                }else{
                    $scope.selection.push(workDateName);

                }
            };
            if (sn !== null && sn !== "" && sn !== undefined) {
                WorkTimeService.get({sn:sn},function(data){
                    $scope.worktime=data;
                });
                $scope.changeDict=function(){
                    $scope.worktime.name="";
                    angular.forEach($scope.selection,function(item){
                        $scope.worktime.name=$scope.worktime.name+item;
                    });
                    var worktime=$scope.worktime;
                    WorkTimeService.update({sn:sn},worktime, function () {
                        $location.path('/worktime');
                    }, function (data) {
                        alert(data);
                    });
                };
            }else{
                $scope.changeWorkTime=function(){
                    $scope.worktime.name="";
                    angular.forEach($scope.selection,function(item){
                        $scope.worktime.name=$scope.worktime.name+item;
                    });
                    var worktime=$scope.worktime;
                  //  alert($scope.worktime.workDays);
                    WorkTimeService.save(worktime,function(){
                        $location.path('/worktime');
                    });
                };
            }
        }
    ]);
