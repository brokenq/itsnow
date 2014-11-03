/**
 * RESTful URL PATTERN
 *
 * GET    /staffs           -> List
 * GET    /staffs/:id       -> Show
 * GET    /staffs/new       -> New
 * POST   /staffs           -> Create
 * GET    /staffs/:id/edit  -> Edit
 * PUT    /staffs/:id       -> Update
 * GET    /staffs/:id/remove-> Remove
 * DELETE /staffs/:id       -> Destroy
 *
 * GET    /staffs/interns
 * GET    /staffs/employees
 *
 * GET    /staffs/:id/contracts
 *
 * staffs/
 *   |- _form.tpl.jade
 *   |- view.tpl.jade
 *   |- new.tpl.jade
 *   |- edit.tpl.jade
 *   |- remove.tpl.jade
 *   |- list.tpl.jade
 *   |- staffs_ctrl.coffee
 */

angular.module('System.Staff.Form', ['multi-select', 'ngResource', 'Lib.Feedback'])

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

    .controller('StaffCtrl', ['$scope', '$location', '$stateParams', 'StaffService', 'SiteService', 'DeptService', 'Feedback',
        function ($scope, $location, $stateParams, staffService, siteService, deptService, feedback) {

            // 提交按钮是否可用，false为可用
            $scope.submited = false;

            // 表单cancel按钮
            $scope.cancel = function () {
                $location.path('/staff');
                //$state.go()
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
                    feedback.success("修改员工'" + staff.name + "'成功");
                    $location.path('/staff');
                }, function (resp) {
                    feedback.error("修改员工'" + staff.name + "'失败", resp);
                });
            };

            // 新建页面提交
            var submitByCreateFun = function () {
                $scope.submited = true;
                var staff = formatSubmitDataFun();
                staffService.save(staff, function () {
                    feedback.success("新建员工'" + staff.name + "'成功");
                    $location.path('/staff');
                }, function (resp) {
                    feedback.error("新建员工'" + staff.name + "'失败", resp);
                });
            };

            // 通过ActionService带过来的参数
            var no = $stateParams.no;
            if (no !== null && no !== "" && no !== undefined) {//进入编辑页面
                // 是否可编辑标记
                $scope.disabled = true;

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

                $scope.submit = submitByEditFun;

            } else { // 进入新建页面
                $scope.disabled = false;

                siteService.query(function (data) {
                    $scope.sites = data;
                });

                deptService.query(function (data) {
                    $scope.departments = data;
                });

                $scope.submit = submitByCreateFun;
            }

        }
    ]);

