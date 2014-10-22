angular.module('System.Site.Form', ['ngResource', 'jcs-autoValidate'])

    .config(function ($stateProvider) {
        $stateProvider.state('site_form', {
            url: '/site_form/{sn}',
            templateUrl: 'system/site/form.tpl.jade',
            data: {pageTitle: '地点管理'}
        });
    })

    // 获取被选中的用户名
    .factory('RoleFormService', ['$scope', function ($scope) {

        var selected = function () {
            var selectedUser = [];
            for (var i in $scope.users) {
                if ($scope.users[i].ticked === true) {
                    var myUser = {};
                    myUser.name = $scope.users[i].name;
                    selectedUser.push(myUser);
                }
            }
            $scope.role.users = selectedUser;
        };

        return selected;
    }
    ])

    .directive('remoteValidation', function ($http) {
        return {
            require: 'ngModel',
            link: function (scope, elm, attrs, ctrl) {
                elm.bind('keyup', function () {
                    $http({method: 'GET', url: '/api/site/' + scope.site.sn}).
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

    .controller('SiteCtrl', ['$scope', '$location', 'SiteService', '$stateParams', 'DictService', 'WorkTimeService',
        function ($scope, $location, siteService, $stateParams, dictService, workTimeService) {

            var sn = $stateParams.sn;

            if (sn !== null && sn !== "" && sn !== undefined) {

                var promise = siteService.get({sn: sn}).$promise;
                promise.then(function (data) {
                    $scope.site = data;

                    var prms = dictService.list({code: 'inc003'}).$promise;
                    prms.then(function (data) {
                        $scope.dictionaries = data;
                        for (var i in $scope.dictionaries) {
                            if ($scope.dictionaries[i].sn == $scope.site.processDictionary.sn) {
                                $scope.dictionary = $scope.dictionaries[i];
                            }
                        }
                    });

                    prms = workTimeService.query().$promise;
                    prms.then(function (data) {
                        $scope.workTimes = data;
                        $scope.workTime = $scope.site.workTime;
                        for (var i in $scope.workTimes) {
                            if ($scope.workTimes[i].sn == $scope.site.workTime.sn) {
                                $scope.workTime = $scope.workTimes[i];
                            }
                        }
                    });

                });

                $scope.submit = function () {

                    var site = $scope.site;
                    site.processDictionary = $scope.dictionary;
                    site.workTime = $scope.workTime;
                    site.$promise = undefined;
                    site.$resolved = undefined;

                    siteService.update({sn: site.sn}, site, function () {
                        $location.path('/site');
                    }, function (data) {
                        alert(data);
                    });
                };

            } else {

                $scope.site = undefined;

                var promise = dictService.list({code: 'inc003'}).$promise;
                promise.then(function (data) {
                    $scope.dictionaries = data;
                });

                promise = workTimeService.query().$promise;
                promise.then(function (data) {
                    $scope.workTimes = data;
                });

                $scope.submit = function () {

                    var site = $scope.site;
                    site.processDictionary = $scope.dictionary;
                    site.workTime = $scope.workTime;

                    siteService.save(site, function () {
                        $location.path('/site');
                    }, function (data) {
                        alert(data);
                    });
                };
            }
        }
    ]);

