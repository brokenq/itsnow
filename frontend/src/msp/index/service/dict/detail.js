// List System
angular.module('Service.Dictnew', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('dict_new', {
            url: '/dict_new',
            templateUrl: 'service/dict/detail.tpl.jade',
            data: {pageTitle: '流程字典添加'}
        });
        $stateProvider.state('dict_edit',{
            url:'/dict_edit/{sn}',
            templateUrl:'service/dict/detail.tpl.jade',
            data:{pageTitle:'流程字典修改'}
        });
    })
    .controller('DictNewCtrl', ['$http','$rootScope','$scope', '$location', '$timeout', '$state','$stateParams', 'DictService',
        function ($http,$rootScope,$scope, $location, $timeout, $state,$stateParams, DictService) {

        var sn=$stateParams.sn;
        $scope.datas=[
            {name: '有效',id:'1',state:'1'},
            {name: '无效',id:'2',state:'2'}
         ];
       // $scope.autoComplete=function(){
           // r=$rootScope.dict.code;
          // alert($scope.dict.code);
          //  $http({method:'GET',url:'/api/process-dictionaries/code/'+r}).success(function(data,status,headers,config){
                //$scope.dicts=data;
           // }).error(function(data,status,headers,config){});
           // alert("失去焦点！");

        //};
        if (sn !== null && sn !== "" && sn !== undefined) {
            DictService.get({sn:sn},function(data){
                $scope.dict=data;
            });
            $scope.changeDict=function(){

                DictService.update({sn:sn}, $scope.dict, function () {
                    $location.path('/dict');
                }, function (data) {
                    alert(data);
                });
            };
         }else{
            $scope.changeDict=function(){
                DictService.save($scope.dict,function(){
                    $location.path('/dict');
                });
            };
        }
}
]);
