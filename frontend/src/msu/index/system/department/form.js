angular.module('System.Department.Form', ['multi-select', 'ngResource', 'Lib.Feedback'])

    .config(function ($stateProvider) {
        $stateProvider.state('dept_edit_form', {
            url: '/dept_form/{sn}',
            templateUrl: 'system/department/form.tpl.jade',
            data: {pageTitle: '部门管理'}
        }).state('dept_new_form', {
            url: '/dept_form/',
            templateUrl: 'system/department/form.tpl.jade',
            data: {pageTitle: '部门管理'}
        });
    })

    .controller('DeptCtrl', ['$scope', '$location', '$stateParams',  'DeptService', 'SiteService', 'Feedback',
        function ($scope, $location, $stateParams, deptService, siteService, feedback) {

            // 提交按钮是否可用，false为可用
            $scope.submited = false;

            // 表单cancel按钮
            $scope.cancel = function () {
                $location.path('/department');
            };

            var selectedSiteFun = function () {
                var selectedSites = [];
                for (var i in $scope.sites) {
                    if ($scope.sites[i].ticked === true) {
                        var mySite = {};
                        mySite.sn = $scope.sites[i].sn;
                        selectedSites.push(mySite);
                    }
                }
                return selectedSites;
            };

            var selectedParentDeptIdFun = function () {
                var parentId;
                delete $scope.parentDepartments.$promise;
                delete $scope.parentDepartments.$resolved;
                for (var i in $scope.parentDepartments) {
                    if ($scope.parentDepartments[i].ticked === true) {
                        parentId = $scope.parentDepartments[i].id;
                    }
                }
                return parentId;
            };

            var formatSubmitDataFun = function () {
                var department = $scope.department;
                department.sites = selectedSiteFun();
                department.parentId = selectedParentDeptIdFun();
                department.$promise = undefined;
                department.$resolved = undefined;
                return department;
            };

            // 编辑页面提交
            var submitByEditFun = function () {
                $scope.submited = true;
                var department = formatSubmitDataFun();
                deptService.update({sn: department.sn}, department, function () {
                    feedback.success("修改部门'" + department.name + "'成功");
                    $location.path('/department');
                }, function (resp) {
                    feedback.error("修改部门'" + department.name + "'失败", resp);
                });
            };

            // 新建页面提交
            var submitByCreateFun = function () {
                $scope.submited = true;
                var department = formatSubmitDataFun();
                deptService.save(department, function () {
                    feedback.success("新建部门'" + department.name + "'成功");
                    $location.path('/department');
                }, function (resp) {
                    feedback.error("新建部门'" + department.name + "'失败", resp);
                });
            };

            var sn = $stateParams.sn;
            if (sn !== null && sn !== "" && sn !== undefined) {

                $("#dept_name").hide();
                $("#dept_name_other").show();

                var promise = deptService.get({sn: sn}).$promise;
                promise.then(function (data) {
                    $scope.department = data;

                    siteService.query(function (data) {
                        $scope.sites = data;
                        for (var i in $scope.sites) {
                            for (var j in $scope.department.sites) {
                                if ($scope.sites[i].sn == $scope.department.sites[j].sn) {
                                    $scope.sites[i].ticked = true;
                                }
                            }
                        }
                    });

                    deptService.query({isTree: true}, function (data) {
                        for (var i in data) {
                            $scope.parentDepartments = data;
                            if ($scope.parentDepartments[i].id == $scope.department.parentId) {
                                $scope.parentDepartments[i].ticked = true;
                            }
                            if($scope.parentDepartments[i].id == $scope.department.id){
                                $scope.parentDepartments.splice(i,1);
                            }
                        }
                    });

                });

                $scope.submit = function () {
                    submitByEditFun();
                };

            } else {
                $("#dept_name").show();
                $("#dept_name_other").hide();

                siteService.query(function (data) {
                    $scope.sites = data;
                });

                deptService.query({isTree: true}, function (data) {
                    $scope.parentDepartments = data;
                });

                $scope.submit = function () {
                    submitByCreateFun();
                };
            }


        }
    ]);

