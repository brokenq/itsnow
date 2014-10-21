angular.module('System.Role.Form', ['multi-select', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('system.role_form', {
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

    .directive('remoteValidation', function($http) {
        return {
            require : 'ngModel',
            link : function(scope, elm, attrs, ctrl) {
                elm.bind('keyup', function() {
                    $http({method: 'GET', url: '/api/roles/'+scope.role.name}).
                        success(function(data, status, headers, config) {
                            if(data===''){
                                ctrl.$setValidity('titleRepeat',true);
                            }else{
                                ctrl.$setValidity('titleRepeat',false);
                            }
                        }).
                        error(function(data, status, headers, config) {
                            ctrl.$setValidity('titleRepeat', false);
                        });
                });
            }
        };
    })

    .controller('RoleCtrl', ['$scope', '$location', 'RoleService', '$stateParams',
        function ($scope, $location, roleService, $stateParams) {

            var name = $stateParams.name;

            if (name !== null && name !== "" && name !== undefined) {

                $("#form-field-mask-1").attr("readonly","true");
                $("#form-field-mask-1").remove("remote-validation");

                var promise = roleService.get({name: name}).$promise;
                promise.then(function(data){
                    $scope.role = data;

                    var prms = roleService.getUsers().$promise;
                    prms.then(function(data){
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

//                    roleFormService.selected();

                    var selectedUser = [];
                    for (var i in $scope.users) {
                        if ($scope.users[i].ticked === true) {
                            var myUser = {};
                            myUser.name = $scope.users[i].name;
                            selectedUser.push(myUser);
                        }
                    }

                    var role = $scope.role;
                    role.users = selectedUser;
                    role.$promise=undefined;
                    role.$resolved=undefined;

                    roleService.update({name: role.name}, role, function () {
                        $location.path('/system/role');
                    }, function (data) {
                        alert(data);
                    });
                };

            } else {

                $("#form-field-mask-1").remove("readonly");
                $("#form-field-mask-1").attr("remote-validation", "");

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

