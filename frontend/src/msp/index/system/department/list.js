// List System
angular.module('System.Department', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('department', {
            url: '/department',
            templateUrl: 'system/department/list.tpl.jade',
            data: {pageTitle: '部门管理'}
        });
    })

    .factory('DeptService', ['$resource', function ($resource) {
        return $resource("/api/departments/:sn", {}, {
            get: { method: 'GET', params: {sn: '@sn'}},
            save: { method: 'POST'},
            update: { method: 'PUT', params: {sn: '@sn'}},
            query: {method: 'GET', params: {isTree: true, keyword: '@keyword'}, isArray: true},
            remove: { method: 'DELETE', params: {sn: '@sn'}}
        });
    }
    ])

    // 过滤拼接地点后的最后一个逗号
    .filter('siteFilter', function () {
        var siteFilter = function (input) {
            var name = '';
            if (input !== null && input !== undefined) {
                for (var i = 0; i < input.length; i++) {
                    name += input[i].name + ',';
                }
                name = name.substring(0, name.length - 1);
            }
            return name || '无';
        };
        return siteFilter;
    })

    .controller('DeptListCtrl', ['$scope', '$location', 'DeptService', 'ngTableParams', 'ActionService',
        function ($scope, $location, deptService, NgTableParams, ActionService) {

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
                    deptService.query(params.url(), function (data, headers) {
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
                deptService.remove({sn: dept.sn},function(){
                    $scope.tableParams.reload();
                });
            };

            $scope.search = function($event){
                if($event.keyCode===13){
                    var promise = deptService.query({keyword:$event.currentTarget.value}).$promise;
                    promise.then(function(data){
                        $scope.departments = data;
                    });
                }
            };

        }
    ]);

