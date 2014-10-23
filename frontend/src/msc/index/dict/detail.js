// List System
angular.module('MscIndex.DictDetail', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('dict_new', {
            url: '/dict_new',
            templateUrl: 'dict/detail.tpl.jade',
            data: {pageTitle: '流程字典添加'}
        });
        $stateProvider.state('dict_edit',{
            url:'/dict_edit/{sn}',
            templateUrl:'dict/detail.tpl.jade',
            data:{pageTitle:'流程字典修改'}
        });
    })
    .controller('DictNewCtrl', ['$rootScope','$scope', '$location', '$timeout', '$state','$stateParams', 'DictService',
        function ($rootScope,$scope, $location, $timeout, $state,$stateParams, DictService) {

        var sn=$stateParams.sn;
        $scope.datas=[
            {name: '有效',id:'1',state:'1'},
            {name: '无效',id:'2',state:'2'}
         ]
        $scope.resetDict=function(){
            $scope.dict={
                 code:'',
                 name:'',
                 display:'',
                 val:'',
                 state:''
            }
        };
        if (sn !== null && sn !== "" && sn !== undefined) {
            DictService.get({sn:sn},function(data){
                $scope.dict=data;
            });
            $scope.changeDict=function(){
                $scope.dict.$promise = undefined;
                $scope.dict.$resolved = undefined;
                DictService.update({sn:sn}, $scope.dict, function () {
                    $location.path('/dict');
                }, function (data) {
                    alert(data);
                });
            }
         }else{
            $scope.changeDict=function(){
                $scope.dict.$promise = undefined;
                $scope.dict.$resolved = undefined;
                DictService.save($scope.dict,function(){
                    $location.path('/dict');
                });
            }
        }
}
]);
