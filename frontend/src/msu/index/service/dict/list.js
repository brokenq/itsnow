// List System
angular.module('Service.Dict', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('dict', {
            url: '/dict',
            templateUrl: 'service/dict/list.tpl.jade',
            data: {pageTitle: '流程字典'}
        });
    })

    .factory('DictService', ['$resource', function ($resource) {
        return $resource("/api/process-dictionaries");
    }
    ])

    .filter('stateFilter', function () {
        var stateFilter = function (input) {
            if(input === '1'){
                return '有效';
            }else{
                return '无效';
            }
        };
        return stateFilter;
    })

    .controller('DictListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'DictService', function ($scope, $location, $timeout, NgTableParams, dictService) {
        var options = {
            page: 1,           // show first page
            count: 10           // count per page
        };

        var args = {
            total: 0,
            getData: function ($defer, params) {
                $location.search(params.url()); // put params in url
                dictService.query(params.url(), function (data, headers) {
                        $timeout(function () {
                                params.total(headers('total'));
                                $defer.resolve($scope.dict = data);
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
            angular.forEach($scope.dict, function (item) {
                if (angular.isDefined(item.sn)) {
                    $scope.checkboxes.items[item.sn] = value;

                }
            });
        });

        // watch for data checkboxes
        $scope.$watch('checkboxes.items', function (values) {
                if (!$scope.dict) {
                    return;
                }
                var checked = 0;
                var unchecked = 0;
                var total = $scope.dict.length;
                angular.forEach($scope.dict, function (item) {
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

