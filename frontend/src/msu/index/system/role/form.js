angular.module('System.Role.Form', ['multi-select', 'ngResource', 'Lib.Feedback'])

    .config(function ($stateProvider) {
        $stateProvider.state('role_edit_form', {
            url: '/role_form/{name}',
            templateUrl: 'system/role/form.tpl.jade',
            data: {pageTitle: '角色管理'}
        }).state('role_new_form', {
            url: '/role_form/',
            templateUrl: 'system/role/form.tpl.jade',
            data: {pageTitle: '角色管理'}
        });
    })

    .controller('RoleCtrl', ['$scope', '$location', '$stateParams', 'RoleService', 'Feedback',
        function ($scope, $location, $stateParams, roleService, feedback) {

            // 提交按钮是否可用，false为可用
            $scope.submited = false;

            // 表单cancel按钮
            $scope.cancel = function () {
                $location.path('/role');
            };

            // 获取下拉复选框选中的用户
            var selectedUserFun = function () {
                var selectedUser = [];
                for (var i in $scope.users) {
                    if ($scope.users[i].ticked === true) {
                        var myUser = {};
                        myUser.name = $scope.users[i].name;
                        selectedUser.push(myUser);
                    }
                }
                return selectedUser;
            };

            // 去除不必要的对象属性，用于HTTP提交
            var formatSubmitDataFun = function () {
                var role = $scope.role;
                role.users = selectedUserFun();
                delete role.$promise;
                delete role.$resolved;
                return role;
            };

            // 编辑页面提交
            var submitByEditFun = function () {
                $scope.submited = true;
                var role = formatSubmitDataFun();
                roleService.update({name: role.name}, role, function () {
                    feedback.success("修改角色'" + role.name + "'成功");
                    $location.path('/role');
                }, function (resp) {
                    feedback.error("修改角色'" + role.name + "'失败", resp);
                });
            };

            // 新建页面提交
            var submitByCreateFun = function () {
                $scope.submited = true;
                var role = formatSubmitDataFun();
                roleService.save(role, function () {
                    feedback.success("新建角色'" + role.name + "'成功");
                    $location.path('/role');
                }, function (resp) {
                    feedback.error("新建角色'" + role.name + "'失败", resp);
                });
            };

            // 通过ActionService带过来的参数
            var name = $stateParams.name;
            if (name !== null && name !== "" && name !== undefined) {//进入编辑页面
                $("#role_name").hide();
                $("#role_name_other").show();

                roleService.get({name: name}, function (data) {
                    $scope.role = data;
                    roleService.getUsers(function (data) {
                        $scope.users = data;
                        for (var i in $scope.users) {
                            for (var j in $scope.role.users) {
                                if ($scope.users[i].name == $scope.role.users[j].name) {
                                    $scope.users[i].ticked = true;
                                }
                            }
                        }
                    });
                });

                $scope.submit = submitByEditFun;

            } else { // 进入新建页面
                $("#role_name").show();
                $("#role_name_other").hide();

                roleService.getUsers(function (data) {
                    $scope.users = data;
                });

                $scope.submit = submitByCreateFun;
            }

        }
    ]);

