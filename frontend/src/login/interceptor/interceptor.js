/**
 * Created by Sin on 2014/8/7.
 */
// CSRF拦截器
angular
    .module('ItsNow.Interceprot', [])

    .factory('CSRFResource', ['$resource', function ($resource) {
        return $resource('security/csrf', null, {
            get: {
                method: 'GET'
            }
        });
    }])

    .factory('CSRFService', ['CSRFResource', function (CSRFResource) {
        return{
            get: function () {
                return CSRFResource.get().$promise;
            }
        };
    }])

    // 拦截器（只拦截POST请求）
    .factory('SessionInjector', ['$injector', '$q',
        function ($injector, $q) {

            var requestInterceptor = {
                request: function(config) {
                    var deferred = $q.defer();
                    if (config.method === 'POST' || config.method === 'PUT' || config.method === 'DELETE') {
//                        console.log("这个是POST/PUT方法");
                        var CSRFService = $injector.get('CSRFService');
                        CSRFService.get().then(function(data){
                            config.headers[data.headerName] = data.token;
                            deferred.resolve(config);
                        });
                    }else{
//                        console.log("这个是GET方法");
                        deferred.resolve(config);
                    }
                    return deferred.promise;
                }
            };

            return requestInterceptor;
        }])

    // 注入拦截器
    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push('SessionInjector');
    }]);
