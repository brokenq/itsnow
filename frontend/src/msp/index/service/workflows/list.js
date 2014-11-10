// List System
angular.module('Service.Workflow', ['ngTable', 'ngResource', 'angularFileUpload'])

    .config(function ($stateProvider) {
        $stateProvider
            .state('workflow', {
                url: '/workflow',
                templateUrl: 'service/workflow/list.tpl.jade',
                data: {pageTitle: '工作流管理'}
            })
            .state('workflow_new', {
                url: '/workflow_new',
                templateUrl: 'service/workflow/form.tpl.jade',
                data: {pageTitle: '新建工作流'}
            });
    })

    .factory('WorkflowService', ['$resource', function ($resource) {
        return $resource("/api/msp_workflows");
    }
    ])

    .controller('WorkflowCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'WorkflowService', 'FileUploader',
        function ($scope, $location, $timeout, NgTableParams, workflowService, FileUploader) {

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


            var uploader = $scope.uploader = new FileUploader({
                url: '/api/msp_workflows'
            });

        }
    ]);

