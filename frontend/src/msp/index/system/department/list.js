// List System
angular.module('System.Department', ['ngTable', 'ngResource', 'Lib.Feedback'])

    .config(function ($stateProvider) {
        $stateProvider.state('department', {
            url: '/department',
            templateUrl: 'system/department/list.tpl.jade',
            data: {pageTitle: '部门管理'}
        });
    })

    .factory('DeptService', ['$resource', function ($resource) {
        return $resource("/api/departments/:sn/:id", {}, {
            get: { method: 'GET', params: {sn: '@sn'}},
            checkChild: { method: 'GET', params: {sn: 'check_child', id: '@id'}, isArray: true},
            save: { method: 'POST'},
            update: { method: 'PUT', params: {sn: '@sn'}},
            query: {method: 'GET', params: {isTree: '@isTree', keyword: '@keyword'}, isArray: true},
            remove: { method: 'DELETE', params: {sn: '@sn'}}
        });
    }
    ])

    // 过滤拼接地点后的最后一个逗号
    .filter('deptFilter', function () {
        return function (input) {
            var name = '';
            if (input !== null && input !== undefined) {
                for (var i = 0; i < input.length; i++) {
                    name += input[i].name + ',';
                }
                name = name.substring(0, name.length - 1);
            }
            return name || '无';
        };
    })

    .controller('DeptListCtrl', ['$scope', '$location', 'DeptService', 'ngTableParams', 'ActionService', 'Feedback',
        function ($scope, $location, deptService, NgTableParams, ActionService, feedback) {

            // ngTable Config
            var options = {
                page: 1,             // show first page
                count: 100           // count per page
            };
            var args = {
                counts: [],          // hide page counts control
                total: 0,            // value less than count hide pagination
                getData: function ($defer, params) {
                    $location.search(params.url()); // put params in url
                    deptService.query({isTree: true}, function (data, headers) {
                            params.total(headers('total'));
                            $defer.resolve($scope.departments = data);
                        }
                    );
                }
            };
            $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args);
            $scope.checkboxes = { 'checked': false, items: {} };

            // ActionService Config
            $scope.getDeptBySn = function (sn) {
                for (var i in $scope.departments) {
                    var dept = $scope.departments[i];
                    if (dept.sn === sn) {
                        return dept;
                    }
                }
            };
            $scope.actionService = new ActionService({watch: $scope.checkboxes.items, mapping: $scope.getDeptBySn});

            // watch for check all checkbox
            $scope.$watch('checkboxes.checked', function (value) {
                angular.forEach($scope.departments, function (item) {
                    if (angular.isDefined(item.sn)) {
                        $scope.checkboxes.items[item.sn] = value;
                    }
                });
            });

            $scope.remove = function (dept) {
                deptService.checkChild({id: dept.id}, function (data) {
                    console.log(data);
                    if (data.length!==0) {
                        feedback.warn("你所选择的部门，下属有子部门，不能进行删除操作！");
                        return false;
                    } else {
                        deptService.remove({sn: dept.sn}, function () {
                            feedback.success("删除部门'" + dept.name + "'成功");
                            delete $scope.checkboxes.items[dept.sn];
                            $scope.tableParams.reload();
                        },function(resp){
                            feedback.error("删除部门'" + dept.name + "'失败", resp);
                        });
                    }
                });
            };

            $scope.search = function ($event) {
                if ($event.keyCode === 13) {
                    var promise = deptService.query({isTree: false, keyword: $event.currentTarget.value}).$promise;
                    promise.then(function (data) {
                        $scope.departments = data;
                    });
                }
            };

        }
    ]);

