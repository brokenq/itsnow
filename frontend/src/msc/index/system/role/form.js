angular.module('System.Role.Form', ['multi-select', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('system.role_edit_form', {
            url: '/role_form/{name}',
            templateUrl: 'system/role/form.tpl.jade',
            data: {pageTitle: '角色管理'}
        }).state('system.role_new_form', {
            url: '/role_form/',
            templateUrl: 'system/role/form.tpl.jade',
            data: {pageTitle: '角色管理'}
        });
    })

    .controller('RoleCtrl', ['$scope', '$location', 'RoleService', '$stateParams',
        function ($scope, $location, roleService, $stateParams) {

            // 提交按钮是否可用，false为可用
            $scope.submited = false;

            // 表单cancel按钮
            $scope.cancel = function () {
                $location.path('/system/role');
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
            var formatRoleFun = function (role) {
                var aRole = role;
                role.users = selectedUserFun();
                delete aRole.$promise;
                delete aRole.$resolved;
                return aRole;
            };

            // 编辑页面提交
            var submitByEditFun = function () {
                $scope.submited = true;
                var role = formatRoleFun($scope.role);
                roleService.update({name: role.name}, role, function () {
                    $location.path('/system/role');
                }, function (data) {
                    alert(data);
                });
            };

            // 新建页面提交
            var submitByCreateFun = function () {
                $scope.submited = true;
                var role = formatRoleFun($scope.role);
                roleService.save(role, function () {
                    $location.path('/system/role');
                }, function (data) {
                    alert(data);
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

                $scope.submit = function () {
                    submitByEditFun();
                };

            } else { // 进入新建页面
                $("#role_name").show();
                $("#role_name_other").hide();

                roleService.getUsers(function (data) {
                    $scope.users = data;
                });

                $scope.submit = function () {
                    submitByCreateFun();
                };
            }

        }
    ]);

