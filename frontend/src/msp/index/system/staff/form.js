angular.module('System.Staff.Form', ['multi-select', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('staff_edit_form', {
            url: '/staff_form/{no}',
            templateUrl: 'system/staff/form.tpl.jade',
            data: {pageTitle: '员工管理'}
        }).state('staff_new_form', {
            url: '/staff_form/',
            templateUrl: 'system/staff/form.tpl.jade',
            data: {pageTitle: '员工管理'}
        });
    })

    .controller('StaffCtrl', ['$scope', '$location', '$stateParams', 'StaffService', 'SiteService', 'DeptService',
        function ($scope, $location, $stateParams, staffService, siteService, deptService) {

            // 提交按钮是否可用，false为可用
            $scope.submited = false;

            // 表单cancel按钮
            $scope.cancel = function () {
                $location.path('/staff');
            };

            // 去除不必要的对象属性，用于HTTP提交
            var formatSubmitDataFun = function () {
                var staff = $scope.staff;
                staff.site = $scope.site;
                staff.department = $scope.department;
                staff.user = $scope.staff_user;
                delete staff.$promise;
                delete staff.$resolved;
                return staff;
            };

            // 编辑页面提交
            var submitByEditFun = function () {
                $scope.submited = true;
                var staff = formatSubmitDataFun();
                staffService.update({no: staff.no}, staff, function () {
                    $location.path('/staff');
                }, function (data) {
                    alert(data);
                });
            };

            // 新建页面提交
            var submitByCreateFun = function () {
                $scope.submited = true;
                var staff = formatSubmitDataFun();
                staffService.save(staff, function () {
                    $location.path('/staff');
                }, function (data) {
                    alert(data);
                });
            };

            // 通过ActionService带过来的参数
            var no = $stateParams.no;
            if (no !== null && no !== "" && no !== undefined) {//进入编辑页面
                $("#staff_no").hide();
                $("#staff_no_other").show();
                $("#username").hide();
                $("#username_other").show();

                staffService.get({no: no}, function (data) {
                    $scope.staff = data;
                    siteService.query(function (data) {
                        $scope.sites = data;
                        for (var i in $scope.sites) {
                            if ($scope.sites[i].sn == $scope.staff.site.sn) {
                                $scope.site = $scope.sites[i];
                            }
                        }
                    });
                    deptService.query(function (data) {
                        $scope.departments = data;
                        for (var i in $scope.departments) {
                            if ($scope.departments[i].sn == $scope.staff.department.sn) {
                                $scope.department = $scope.departments[i];
                            }
                        }
                    });
                });

                $scope.submit = function () {
                    submitByEditFun();
                };

            } else { // 进入新建页面
                $("#staff_no").show();
                $("#staff_no_other").hide();
                $("#username").show();
                $("#username_other").hide();

                siteService.query(function (data) {
                    $scope.sites = data;
                });

                deptService.query(function (data) {
                    $scope.departments = data;
                });

                $scope.submit = function () {
                    submitByCreateFun();
                };
            }

        }
    ]);

