// List System
angular.module('System.Site', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('site', {
            url: '/site',
            templateUrl: 'system/site/list.tpl.jade',
            data: {pageTitle: '地点管理'}
        });
    })

    .factory('SiteService', ['$resource', function ($resource) {
        return $resource("/api/sites");
    }
    ])

    .controller('SiteListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'SiteService', function ($scope, $location, $timeout, NgTableParams, siteService) {
        var options = {
            page: 1,           // show first page
            count: 10           // count per page
        };

        var args = {
            total: 0,
            getData: function ($defer, params) {
                $location.search(params.url()); // put params in url
                siteService.query(params.url(), function (data, headers) {
                        $timeout(function () {
                                params.total(headers('total'));
                                $defer.resolve($scope.site = data);
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
            angular.forEach($scope.site, function (item) {
                if (angular.isDefined(item.sn)) {
                    $scope.checkboxes.items[item.sn] = value;

                }
            });
        });

        // watch for data checkboxes
        $scope.$watch('checkboxes.items', function (values) {
                if (!$scope.site) {
                    return;
                }
                var checked = 0;
                var unchecked = 0;
                var total = $scope.site.length;
                angular.forEach($scope.site, function (item) {
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

