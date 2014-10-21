angular.module('System.Role.Form', ['multi-select', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('role_form', {
            url: '/role_form/{name}',
            templateUrl: 'system/role/form.tpl.jade',
            data: {pageTitle: '角色管理'}
        });
    })

    // 获取被选中的用户名
    .factory('RoleFormService', ['$scope', function ($scope) {

        var selected = function () {
            var selectedUser = [];
            for (var i in $scope.users) {
                if ($scope.users[i].ticked === true) {
                    var myUser = {};
                    myUser.name = $scope.users[i].name;
                    selectedUser.push(myUser);
                }
            }
            $scope.role.users = selectedUser;
        };

        return selected;
    }
    ])

    .controller('RoleCtrl', ['$scope', '$location', 'RoleService', '$stateParams',
        function ($scope, $location, roleService, $stateParams) {



            var name = $stateParams.name;

            if (name !== null && name !== "" && name !== undefined) {

                $("#form-field-mask-1").attr("readonly","true");

                roleService.get({name: name}, function (data) {
                    $scope.role = data;

                    roleService.getUsers(
                        function (data) {
                            $scope.users = data;
                            for (var i in $scope.users) {
                                for (var j in $scope.role.users) {
                                    if ($scope.users[i].name == $scope.role.users[j].name) {
                                        $scope.users[i].ticked = true;
                                    }
                                    /*else{
                                     $scope.users[i].ticked = false;
                                     }*/
                                }
                            }
                        }
                    );

                });

                $scope.submit = function () {

//                    roleFormService.selected();

                    var selectedUser = [];
                    for (var i in $scope.users) {
                        if ($scope.users[i].ticked === true) {
                            var myUser = {};
                            myUser.name = $scope.users[i].name;
                            selectedUser.push(myUser);
                        }
                    }
                    $scope.role.users = selectedUser;

                    roleService.update({name: $scope.role.name}, $scope.role, function () {
                        $location.path('/system/role');
                    }, function (data) {
                        alert(data);
                    });
                };

            } else {

                $("#form-field-mask-1").remove("readonly");

                roleService.getUsers(
                    function (data) {
                        $scope.users = data;
                    }
                );

                $scope.submit = function () {

//                    roleFormService.selected();

                    var selectedUser = [];
                    for (var i in $scope.users) {
                        if ($scope.users[i].ticked === true) {
                            var myUser = {};
                            myUser.name = $scope.users[i].name;
                            selectedUser.push(myUser);
                        }
                    }
                    $scope.role.users = selectedUser;

                    roleService.save($scope.role, function () {
                        $location.path('/system/role');
                    }, function (data) {
                        alert(data);
                    });
                };
            }

        }
    ]);

