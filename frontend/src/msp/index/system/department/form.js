angular.module('System.Department.Form', ['multi-select', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('dept_form', {
            url: '/dept_form/{sn}',
            templateUrl: 'system/department/form.tpl.jade',
            data: {pageTitle: '部门管理'}
        });
    })

    .directive('remoteValidationDeptName', function ($http) {
        return {
            require: 'ngModel',
            link: function (scope, elm, attrs, ctrl) {
                elm.bind('keyup', function () {
                    $http({method: 'GET', url: '/api/departments/' + scope.department.name}).
                        success(function (data, status, headers, config) {
                            if (data === '') {
                                ctrl.$setValidity('titleRepeat', true);
                            } else {
                                ctrl.$setValidity('titleRepeat', false);
                            }
                        }).
                        error(function (data, status, headers, config) {
                            ctrl.$setValidity('titleRepeat', false);
                        });
                });
            }
        };
    })

    .controller('DeptCtrl', ['$scope', '$location', 'DeptService', '$stateParams', 'SiteService',
        function ($scope, $location, deptService, $stateParams, siteService) {

            var sn = $stateParams.sn;

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

            if (sn !== null && sn !== "" && sn !== undefined) {

                $("#form-field-mask-1").remove("remote-validation-dept-name");

                var promise = deptService.get({sn: sn}).$promise;
                promise.then(function (data) {
                    $scope.department = data;

                    var prms = siteService.query().$promise;
                    prms.then(function (data) {
                        $scope.sites = data;
                        for (var i in $scope.sites) {
                            for (var j in $scope.department.sites) {
                                if ($scope.sites[i].sn == $scope.department.sites[j].sn) {
                                    $scope.sites[i].ticked = true;
                                }
                            }
                        }
                    });

                });

                $scope.submit = function () {

                    var selectedSite = selectedSiteFun();

                    var department = $scope.department;
                    department.sites = selectedSite;
                    department.$promise = undefined;
                    department.$resolved = undefined;

                    deptService.update({name: department.name}, department, function () {
                        $location.path('/dept');
                    }, function (data) {
                        alert(data);
                    });
                };

            } else {

                $("#form-field-mask-1").attr("remote-validation-dept-name", "");

                siteService.query(function (data) {
                    $scope.sites = data;
                });

                $scope.submit = function () {

                    var selectedSite = selectedSiteFun();

                    var department = $scope.department;
                    department.sites = selectedSite;

                    deptService.save($scope.department, function () {
                        $location.path('/department');
                    }, function (data) {
                        alert(data);
                    });
                };
            }


        }
    ]);

