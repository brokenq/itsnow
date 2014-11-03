// List System
angular.module('MscIndex.User.Detail', [ 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('createUserDetail', {
            url: '/createUserDetail',
            templateUrl: 'system/user/detail.tpl.jade',
            data: {pageTitle: '用户添加'}
        });
        $stateProvider.state('updateUserDetail',{
            url:'/updateUserDetail/{username}',
            templateUrl:'system/user/edit.tpl.jade',
            data:{pageTitle:'用户修改'}
        });
    })
    .controller('UserDetailCtrl', ['$rootScope','$scope', '$location', '$timeout', '$state','$stateParams', 'UserService',
        function ($rootScope,$scope, $location, $timeout, $state,$stateParams, UserService) {
            var username=$stateParams.username;
            $scope.datas=[
                {name: '启用',id:'1',state:true},
                {name: '停用',id:'2',state:false}
            ];
            $scope.validatePassword=function(){
                if(($scope.cuser.password)!==($scope.cuser.repeatPassword)){
                    $scope.cuser.repeatPassword="";
                    alert("输入密码不一致，请重新输入！！");
                }
            };
            if (username !== null && username !== "" && username!== undefined) {
                UserService.get({username:username},function(data){
                    if(data.enabled){
                        $scope.selectstate = $scope.datas[0];
                    }else{
                        $scope.selectstate = $scope.datas[1];
                    }
                    $scope.cuser=data;
                });
                $scope.updateUser=function(){
                    $scope.cuser.enabled=$scope.selectstate.state;
                    $scope.cuser.$promise = undefined;
                    $scope.cuser.$resolved = undefined;
                    UserService.update({username:username},$scope.cuser,function(){
                        $location.path('/user');
                    });
                };

            }else{
                $scope.createUser=function(){
                    $scope.cuser.$promise = undefined;
                    $scope.cuser.$resolved = undefined;
                    UserService.save($scope.cuser,function(){
                        $location.path('/user');
                    });
                };
            }
        }
    ]);
