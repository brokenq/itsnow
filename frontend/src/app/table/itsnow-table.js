/**
 * Created by Sin on 2014/8/18.
 */
var appModule = angular.module('App.Table',['ngGrid', 'App.Dialog']);

appModule.directive('hello', function(){
    return {
        restrict: 'E',
        templateUrl: 'itsnow-table.tpl.html',
        replace: true,
        transclude : true,
        controller : function($rootScope, $scope, $element, $location, DialogService){

            $scope.mySelections = [];

            $scope.filterOptions = {
                filterText: "",
                useExternalFilter: true
            };

            $scope.totalServerItems = 0;

            $scope.pagingOptions = {
                pageSizes: [20],
                pageSize: 20,
                currentPage: 1
            };

            $scope.setPagingData = function (data, page, pageSize) {
                var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
                $scope.datas = pagedData;
                $scope.totalServerItems = data.length;
//                if (!$scope.$$phase) {
//                    $scope.$apply();
//                }
            };

            $scope.getPagedDataAsync = function (pageSize, page, searchText) {
                setTimeout(function () {
                    var data;
                    if (searchText) {
                        var ft = searchText.toLowerCase();
                        $scope.ListService.list(function (largeLoad) {
                            data = largeLoad.filter(function (item) {
                                return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                            });
                            $scope.setPagingData(data, page, pageSize);
                        });
                    } else {
                        $scope.ListService.list(function (largeLoad) {
                            $scope.setPagingData(largeLoad, page, pageSize);
                        });
                    }
                }, 100);
            };

            $scope.getPagedDataAsyncBySearch = function (pageSize, page, searchText) {
                setTimeout(function () {
                    $scope.SearchService.query({key:searchText}, function (largeLoad) {
                        $scope.setPagingData(largeLoad, page, pageSize);
                    });
                }, 100);
            };

            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

            $scope.$watch('pagingOptions', function (newVal, oldVal) {
                if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
                    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
                }
            }, true);

            $scope.$watch('filterOptions', function (newVal, oldVal) {
                if (newVal !== oldVal) {
                    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
                }
            }, true);

            // 故障单列表
            $scope.gridUserList = {
                data: 'datas',
                enablePaging: true,
                showFooter: true,
                totalServerItems: 'totalServerItems',
                pagingOptions: $scope.pagingOptions,
                filterOptions: $scope.filterOptions,
                multiSelect: false,
                showSelectionCheckbox: true,
                selectedItems: $scope.mySelections,
                columnDefs: $scope.columnDefs
            };

            $scope.searchFun = function (){
                $scope.getPagedDataAsyncBySearch($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.searchKey);
            };

            $scope.newFun = function (){
                $location.path($scope.newUrl);
            };

            $scope.acceptFun = function (){

                var promise;
                var taskName = '';

                for(var ticket in $scope.mySelections){
                    $rootScope.incident = $scope.mySelections[ticket];
                    promise = $scope.QueryTaskService.query($rootScope.incident.instanceId);
                };

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        taskName = data.tasks[i].taskName;
                    }
                    if(taskName.indexOf('accept')>-1){
                        $location.path($scope.acceptUrl);
                    }else{
                        DialogService('error','流程错误！','下一步处理应为：'+taskName);
                    };
                }, function error(msg) {
                        DialogService('error','','出错：'+msg);
                });
            };

            $scope.analysisFun = function (){

                var promise;
                var taskName = '';

                for(var ticket in $scope.mySelections){
                    $rootScope.incident = $scope.mySelections[ticket];
                    promise = $scope.QueryTaskService.query($rootScope.incident.instanceId);
                };

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        var task = data.tasks[i];
                        taskName = task.taskName;
                    }

                    if(taskName.indexOf('analysis')>-1){
                        $location.path($scope.analysisUrl);
                    }else{
                        DialogService('error','流程错误！','下一步处理应为：'+taskName);
                    };

                }, function error(msg) {
                    console.error(msg);
                });
            };

            $scope.processFun = function (){

                var promise;
                var taskName = '';

                for(var ticket in $scope.mySelections){
                    $rootScope.incident = $scope.mySelections[ticket];
                    promise = $scope.QueryTaskService.query($rootScope.incident.instanceId);
                };

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        var task = data.tasks[i];
                        taskName = task.taskName;
                    }

                    if(taskName.indexOf('process')>-1){
                        $location.path($scope.processUrl);
                    }else{
                        DialogService('error','流程错误！','下一步处理应为：'+taskName);
                    };

                }, function error(msg) {
                    console.error(msg);
                });
            };

            $scope.closeFun = function (){

                var promise;
                var taskName = '';

                for(var ticket in $scope.mySelections){
                    $rootScope.incident = $scope.mySelections[ticket];
                    promise = $scope.QueryTaskService.query($rootScope.incident.instanceId);
                };

                promise.then(function success(data) {
                    for (var i in data.tasks) {
                        var task = data.tasks[i];
                        taskName = task.taskName;
                    }

                    if(taskName.indexOf('close')>-1){
                        $location.path($scope.closeUrl);
                    }else{
                        DialogService('error','流程错误！','下一步处理应为：'+taskName);
                    };

                }, function error(msg) {
                    console.error(msg);
                });
            };

        },
        link : function(scope, element, attrs) {
//                console.log(angular.element(element.find('div')));
            scope.$watch('mySelections', function (newVal, oldVal) {
//                console.log('newVal:');
//                console.log(newVal);
                if(newVal.length>0){
                    var taskName = '';
                    var promise = scope.QueryTaskService.query(newVal[0].msuInstanceId);
                    promise.then(function success(data) {
                        for (var i in data.tasks) {
                            var task = data.tasks[i];
                            taskName = task.taskName;
                        }
                        console.log("taskName:"+taskName);
                        if(taskName.indexOf('accept')>-1){
                            angular.element(element.find('button#accept')).removeAttr('disabled');
                            angular.element(element.find('button#analysis')).attr('disabled','disabled');
                            angular.element(element.find('button#process')).attr('disabled','disabled');
                            angular.element(element.find('button#close')).attr('disabled','disabled');
                        };
                        if(taskName.indexOf('analysis')>-1){
                            angular.element(element.find('button#accept')).attr('disabled','disabled');
                            angular.element(element.find('button#analysis')).removeAttr('disabled');
                            angular.element(element.find('button#process')).attr('disabled','disabled');
                            angular.element(element.find('button#close')).attr('disabled','disabled');
                        };
                        if(taskName.indexOf('process')>-1){
                            angular.element(element.find('button#accept')).attr('disabled','disabled');
                            angular.element(element.find('button#analysis')).attr('disabled','disabled');
                            angular.element(element.find('button#process')).removeAttr('disabled');
                            angular.element(element.find('button#close')).attr('disabled','disabled');
                        };
                        if(taskName.indexOf('close')>-1){
                            angular.element(element.find('button#accept')).removeAttr('disabled');
                            angular.element(element.find('button#analysis')).removeAttr('disabled');
                            angular.element(element.find('button#process')).removeAttr('disabled');
                            angular.element(element.find('button#close')).attr('disabled','disabled');
                        };

                    }, function error(msg) {
                        console.error(msg);
                    });
                }else{
                    angular.element(element.find('button#accept')).attr('disabled','disabled');
                    angular.element(element.find('button#analysis')).attr('disabled','disabled');
                    angular.element(element.find('button#process')).attr('disabled','disabled');
                    angular.element(element.find('button#close')).attr('disabled','disabled');
                }

            }, true);

        }
    }
    });