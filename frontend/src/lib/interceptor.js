/**
 * Created by Sin on 2014/8/7.
 */
// CSRF拦截器
angular
    .module('Lib.Interceptor', [])

    .factory('CSRFService', ['$resource', function ($resource) {
        return $resource('security/csrf', {
            get: { method: 'GET' }
        });
    }])

    // 拦截器（只拦截POST请求）
    .factory('SessionInjector', ['$injector', '$q',
        function ($injector, $q) {
            return {
                request: function (config) {
                    $("#loading").show();
                    var deferred = $q.defer();
                    if (config.method === 'POST' || config.method === 'PUT' || config.method === 'DELETE') {
                        var CSRFService = $injector.get('CSRFService');
                        var promise = CSRFService.get().$promise;
                        promise.then(function (data) {
                            config.headers[data.headerName] = data.token;
                            deferred.resolve(config);
                        });
                    } else {
                        deferred.resolve(config);
                    }
                    return deferred.promise;
                },
                requestError: function(rejection) {
                    $("#loading").show();
                    return $q.reject(rejection);
                },
                response: function (response) {
                    $("#loading").hide();
                    return response;
                },
                responseError: function(rejection) {
                    $("#loading").hide();
                    return $q.reject(rejection);
                }
            };
        }])

    // 注入拦截器
    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push('SessionInjector');
    }]);
