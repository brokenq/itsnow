// List System
angular.module('Service.Workflow', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('workflow', {
            url: '/workflow',
            templateUrl: 'service/workflow/list.tpl.jade',
            data: {pageTitle: '工作流管理'}
        });
    })

    .factory('WorkflowService', ['$resource', function ($resource) {
        return $resource("/api/msu_workflows");
    }
    ])

    .controller('WorkflowListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'WorkflowService', function ($scope, $location, $timeout, NgTableParams, workflowService) {
        var options = {
            page: 1,           // show first page
            count: 10           // count per page
        };

        var args = {
            total: 0,
            getData: function ($defer, params) {
                $location.search(params.url()); // put params in url
                workflowService.query(params.url(), function (data, headers) {
                        $timeout(function () {
                                params.total(headers('total'));
                                $defer.resolve($scope.workflow = data);
                            },
                            500
                        );
                    }
                );
            }
        };
        $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args);
        $scope.checkboxes = { 'checked': false, items: {} };

        // watch for check all checkbox
        $scope.$watch('checkboxes.checked', function (value) {
            angular.forEach($scope.workflow, function (item) {
                if (angular.isDefined(item.sn)) {
                    $scope.checkboxes.items[item.sn] = value;

                }
            });
        });

        // watch for data checkboxes
        $scope.$watch('checkboxes.items', function (values) {
                if (!$scope.workflow) {
                    return;
                }
                var checked = 0;
                var unchecked = 0;
                var total = $scope.workflow.length;
                angular.forEach($scope.workflow, function (item) {
                    checked += ($scope.checkboxes.items[item.sn]) || 0;
                    unchecked += (!$scope.checkboxes.items[item.sn]) || 0;
                });
                if ((unchecked === 0) || (checked === 0)) {
                    $scope.checkboxes.checked = (checked == total);
                }
                // grayed checkbox
                angular.element(document.getElementById("select_all")).prop("indeterminate", (checked !== 0 && unchecked !== 0));
            },
           true
        );

    }
    ]);

