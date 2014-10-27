angular.module('System.Site.Form', ['ngResource', 'jcs-autoValidate'])

    .config(function ($stateProvider) {
        $stateProvider.state('site_edit_form', {
            url: '/site_form/{sn}',
            templateUrl: 'system/site/form.tpl.jade',
            data: {pageTitle: '地点管理'}
        }).state('site_new_form', {
            url: '/site_form/',
            templateUrl: 'system/site/form.tpl.jade',
            data: {pageTitle: '地点管理'}
        });
    })

    .controller('SiteCtrl', ['$scope', '$location', '$stateParams', 'SiteService', 'DictService', 'WorkTimeService',
        function ($scope, $location, $stateParams, siteService, dictService, workTimeService) {

            // 提交按钮是否可用，false为可用
            $scope.submited = false;

            // 表单cancel按钮
            $scope.cancel = function () {
                $location.path('/site');
            };

            // 去除不必要的对象属性，用于HTTP提交
            var formatSubmitDataFun = function () {
                var site = $scope.site;
                site.dictionary = $scope.dictionary;
                site.workTime = $scope.workTime;
                delete site.$promise;
                delete site.$resolved;
                return site;
            };

            // 编辑页面提交
            var submitByEditFun = function () {
                $scope.submited = true;
                var site = formatSubmitDataFun();
                siteService.update({sn: site.sn}, site, function () {
                    $location.path('/site');
                }, function (data) {
                    alert(data);
                });
            };

            // 新建页面提交
            var submitByCreateFun = function () {
                $scope.submited = true;
                var site = formatSubmitDataFun();
                siteService.save(site, function () {
                    $location.path('/site');
                }, function (data) {
                    alert(data);
                });
            };

            var sn = $stateParams.sn;
            if (sn !== null && sn !== "" && sn !== undefined) {

                $("#site_name").hide();
                $("#site_name_other").show();

                var promise = siteService.get({sn: sn}).$promise;
                promise.then(function (data) {
                    $scope.site = data;

                    dictService.list({code: 'inc003'}, function (data) {
                        $scope.dictionaries = data;
                        for (var i in $scope.dictionaries) {
                            if ($scope.dictionaries[i].sn == $scope.site.dictionary.sn) {
                                $scope.dictionary = $scope.dictionaries[i];
                            }
                        }
                    });

                    workTimeService.query(function (data) {
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
                    submitByEditFun();
                };

            } else {
                $("#site_name").show();
                $("#site_name_other").hide();

                dictService.list({code: 'inc003'}, function (data) {
                    $scope.dictionaries = data;
                });

                workTimeService.query(function (data) {
                    $scope.workTimes = data;
                });

                $scope.submit = function () {
                    submitByCreateFun();
                };
            }
        }
    ]);

