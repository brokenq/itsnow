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
            };
         }else{
            var dictdates={};
            DictService.query(function(dicts){
                dictdates=dicts;

            });
            $scope.autoComCode=function(){
                for(var i= 0;i<dictdates.length;i++){

                    if($scope.dict.code===dictdates[i].code){
                        $scope.dict.name=dictdates[i].name;
                        break;
                    }
                }
             $scope.autoValidate=function(){
               for(var i=0;i<dictdates.length;i++){
                   if($scope.dict.code===dictdates[i].code){
                       if($scope.dict.display===dictdates[i].display){
                           $scope.dict.display="";
                           alert("显示名已存在！！请重新输入");
                       }
                   }
               }
             };
              //  angular.forEach(dictdates,function(dictdata){
                 //alert(code);

                //    if($scope.dict.code===dictdata.code){
                  //      $scope.dict.name=dictdata.name;

                    //}
               // });
            };


            $scope.changeDict=function(){
                $scope.dict.$promise = undefined;
                $scope.dict.$resolved = undefined;
                DictService.save($scope.dict,function(){
                    $location.path('/dict');
                });
            };
        }
}
]);
