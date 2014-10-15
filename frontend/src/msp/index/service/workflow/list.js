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

    .controller('WorkflowCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'WorkflowService', '$upload',
        function ($scope, $location, $timeout, NgTableParams, workflowService, $upload) {

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

//            $scope.onFileSelect = function ($files) {
//                $scope.selectedFiles = [];
//                $scope.progress = [];
//                if ($scope.upload && $scope.upload.length > 0) {
//                    for (var i = 0; i < $scope.upload.length; i++) {
//                        if ($scope.upload[i] != null) {
//                            $scope.upload[i].abort();
//                        }
//                    }
//                }
//                $scope.upload = [];
//                $scope.uploadResult = [];
//                $scope.selectedFiles = $files;
//                $scope.dataUrls = [];
//                for (var i = 0; i < $files.length; i++) {
//                    var $file = $files[i];
//                    if ($scope.fileReaderSupported && $file.type.indexOf('image') > -1) {
//                        var fileReader = new FileReader();
//                        fileReader.readAsDataURL($files[i]);
//                        var loadFile = function (fileReader, index) {
//                            fileReader.onload = function (e) {
//                                $timeout(function () {
//                                    $scope.dataUrls[index] = e.target.result;
//                                });
//                            };
//                        }(fileReader, i);
//                    }
//                    $scope.progress[i] = -1;
//                    if ($scope.uploadRightAway) {
//                        $scope.start(i);
//                    }
//                }
//            };

//            $scope.start = function (index) {
//                $scope.progress[index] = 0;
//                $scope.errorMsg = null;
//                if ($scope.howToSend == 1) {
//                    //$upload.upload()
//                    $scope.upload[index] = $upload.upload({
//                        url: uploadUrl,
//                        method: $scope.httpMethod,
//                        headers: {'my-header': 'my-header-value'},
//                        data: {
//                            myModel: $scope.myModel,
//                            errorCode: $scope.generateErrorOnServer && $scope.serverErrorCode,
//                            errorMessage: $scope.generateErrorOnServer && $scope.serverErrorMsg
//                        },
//                        /* formDataAppender: function(fd, key, val) {
//                         if (angular.isArray(val)) {
//                         angular.forEach(val, function(v) {
//                         fd.append(key, v);
//                         });
//                         } else {
//                         fd.append(key, val);
//                         }
//                         }, */
//                        /* transformRequest: [function(val, h) {
//                         console.log(val, h('my-header')); return val + '-modified';
//                         }], */
//                        file: $scope.selectedFiles[index],
//                        fileFormDataName: 'myFile'
//                    });
//                    $scope.upload[index].then(function (response) {
//                        $timeout(function () {
//                            $scope.uploadResult.push(response.data);
//                        });
//                    }, function (response) {
//                        if (response.status > 0) $scope.errorMsg = response.status + ': ' + response.data;
//                    }, function (evt) {
//                        // Math.min is to fix IE which reports 200% sometimes
//                        $scope.progress[index] = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
//                    });
//                    $scope.upload[index].xhr(function (xhr) {
////				xhr.upload.addEventListener('abort', function() {console.log('abort complete')}, false);
//                    });
//                } else if ($scope.howToSend == 2) {
//                    //$upload.http()
//                    var fileReader = new FileReader();
//                    fileReader.onload = function (e) {
//                        console.log('file is loaded in filereader');
//                        $scope.upload[index] = $upload.http({
//                            url: uploadUrl,
//                            headers: {'Content-Type': $scope.selectedFiles[index].type},
//                            data: e.target.result
//                        });
//                        $scope.upload[index].then(function (response) {
//                            $scope.uploadResult.push(response.data);
//                        }, function (response) {
//                            if (response.status > 0) $scope.errorMsg = response.status + ': ' + response.data;
//                        }, function (evt) {
//                            // Math.min is to fix IE which reports 200% sometimes
//                            $scope.progress[index] = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
//                            console.log('progres', Math.min(100, parseInt(100.0 * evt.loaded / evt.total)))
//                        });
//                        $scope.upload[index].xhr(function (xhr) {
//                            xhr.upload.addEventListener('progress', function (evt) {
//                                console.log('progres2', Math.min(100, parseInt(100.0 * evt.loaded / evt.total)))
//                            }, false);
//                            xhr.addEventListener('progress', function (evt) {
//                                console.log('progres3', Math.min(100, parseInt(100.0 * evt.loaded / evt.total)))
//                            }, false);
//                        });
//                    };
//                    fileReader.readAsArrayBuffer($scope.selectedFiles[index]);
//                } else {
//                    //s3 upload
//                    $scope.upload[index] = $upload.upload({
//                        url: $scope.s3url,
//                        method: 'POST',
//                        data: {
//                            key: $scope.selectedFiles[index].name,
//                            AWSAccessKeyId: $scope.AWSAccessKeyId,
//                            acl: $scope.acl,
//                            policy: $scope.policy,
//                            signature: $scope.signature,
//                            "Content-Type": $scope.selectedFiles[index].type === null || $scope.selectedFiles[index].type === '' ?
//                                'application/octet-stream' : $scope.selectedFiles[index].type,
//                            filename: $scope.selectedFiles[index].name
//                        },
//                        file: $scope.selectedFiles[index],
//                        fileFormDataName: 'myFile'
//                    });
//                    $scope.upload[index].then(function (response) {
//                        $timeout(function () {
//                            $scope.uploadResult.push(response.data);
//                        });
//                    }, function (response) {
//                        if (response.status > 0) $scope.errorMsg = response.status + ': ' + response.data;
//                    }, function (evt) {
//                        // Math.min is to fix IE which reports 200% sometimes
//                        $scope.progress[index] = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
//                    });
//                    if (localStorage) {
//                        localStorage.setItem("s3url", $scope.s3url);
//                        localStorage.setItem("AWSAccessKeyId", $scope.AWSAccessKeyId);
//                        localStorage.setItem("acl", $scope.acl);
//                        localStorage.setItem("success_action_redirect", $scope.success_action_redirect);
//                        localStorage.setItem("policy", $scope.policy);
//                        localStorage.setItem("signature", $scope.signature);
//                    }
//                }
//            };

        }
    ]);

