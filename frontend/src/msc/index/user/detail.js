// List System
angular.module('MscIndex.User.Detail', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('createUserDetail', {
            url: '/createUserDetail',
            templateUrl: 'user/detail.tpl.jade',
            data: {pageTitle: '用户添加'}
        });
        $stateProvider.state('updateUserDetail',{
            url:'/updateUserDetail/{sn}',
            templateUrl:'user/detail.tpl.jade',
            data:{pageTitle:'用户修改'}
        });
    })
    .controller('UserDetailCtrl', ['$rootScope','$scope', '$location', '$timeout', '$state','$stateParams', 'UserService',
        function ($rootScope,$scope, $location, $timeout, $state,$stateParams, UserService) {
            var sn=$stateParams.sn;
            $scope.datas=[
                {name: '启用',id:'1',state:'1'},
                {name: '停用',id:'2',state:'2'}
            ];
            if (sn !== null && sn !== "" && sn !== undefined) {

            }else{

            }
        }
    ]);
