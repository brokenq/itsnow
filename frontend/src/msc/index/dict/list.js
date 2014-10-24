// List System
angular.module('MscIndex.Dict', ['ngTable', 'ngResource', 'dnt.action.service','MscIndex.DictDetail'])

    .config(function ($stateProvider) {
        $stateProvider.state('dict', {
            url: '/dict',
            templateUrl: 'dict/list.tpl.jade',
            data: {pageTitle: '流程字典管理'}
        });
    })

    .factory('DictService', ['$resource', function ($resource) {
        return $resource(" /api/process-dictionaries/:sn",{},{
            get: { method: 'GET', params: {sn: '@sn'}},
            save: { method: 'POST'},
            update: { method: 'PUT', params: {sn: '@sn'}},
            query: { method: 'GET', isArray: true},
            remove: { method: 'DELETE', params: {sn: '@sn'}}
        });
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

    .controller('DictListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'DictService', 'ActionService',
        function ($scope,$location,$timeout,NgTableParams,dictService,ActionService) {
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
                                $defer.resolve($scope.dicts = data);
                            },
                            500
                        );
                    }
                );
            }
        };
        $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args);
        $scope.checkboxes = { 'checked': false, items: {} };
        $scope.getDictBySn  = function(sn){
            for(var i in $scope.dicts){
                var dict = $scope.dicts[i];
                if(dict.sn===sn){
                    $scope.dict = dict;
                    return dict;
                }
            }
        };
        $scope.actionService = new ActionService({watch: $scope.checkboxes.items, mapping: $scope.getDictBySn});
        // watch for check all checkbox
        $scope.$watch('checkboxes.checked', function (value) {
            angular.forEach($scope.dicts, function (item) {
                if (angular.isDefined(item.sn)) {
                    $scope.checkboxes.items[item.sn] = value;

                }
            });
        });
        $scope.deleteDict = function (dict) {
                dictService.remove({sn: dict.sn},function(){
                    $scope.tableParams.reload();
                });
            };
        $scope.refresh=function(){
            $scope.tableParams.reload();
        };
        // watch for data checkboxes
        $scope.$watch('checkboxes.items', function (values) {
                if (!$scope.dicts) {
                    return;
                }
                var checked = 0;
                var unchecked = 0;
                var total = $scope.dicts.length;
                angular.forEach($scope.dicts, function (item) {
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

