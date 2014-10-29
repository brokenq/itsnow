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
        return $resource("/api/sites/:sn", {}, {
            get: { method: 'GET', params: {sn: '@sn'}},
            save: { method: 'POST'},
            update: { method: 'PUT', params: {sn: '@sn'}},
            query: { method: 'GET', params: {keyword: '@keyword'}, isArray: true},
            remove: { method: 'DELETE', params: {sn: '@sn'}},
            getWorkTimes: { method: 'GET', params: {name: 'workTime'}, isArray: true}
        });
    }
    ])

    .controller('SiteListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'SiteService', 'ActionService',
        function ($scope, $location, $timeout, NgTableParams, siteService, ActionService) {

            var options = {
                page: 1,           // show first page
                count: 5           // count per page
            };
            var args = {
                total: 0,
                getData: function ($defer, params) {
                    $location.search(params.url()); // put params in url
                    siteService.query(params.url(), function (data, headers) {
                            $timeout(function () {
                                    params.total(headers('total'));
                                    $defer.resolve($scope.sites = data);
                                },
                                500
                            );
                        }
                    );
                }
            };
            $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args);
            $scope.checkboxes = { 'checked': false, items: {} };

            $scope.getSiteBySn = function (sn) {
                for (var i in $scope.sites) {
                    var site = $scope.sites[i];
                    if (site.sn === sn) {
                        return site;
                    }
                }
            };
            $scope.actionService = new ActionService({watch: $scope.checkboxes.items, mapping: $scope.getSiteBySn});

            // watch for check all checkbox
            $scope.$watch('checkboxes.checked', function (value) {
                angular.forEach($scope.sites, function (item) {
                    if (angular.isDefined(item.sn)) {
                        $scope.checkboxes.items[item.sn] = value;
                    }
                });
            });

            $scope.remove = function (site) {
                siteService.remove({sn: site.sn},function(){
                    delete $scope.checkboxes.items[site.sn];
                    $scope.tableParams.reload();
                });
            };

            $scope.search = function($event){
                if($event.keyCode===13){
                    var promise = siteService.query({keyword:$event.currentTarget.value}).$promise;
                    promise.then(function(data){
                        $scope.sites = data;
                    });
                }
            };

        }
    ]);

