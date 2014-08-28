/**
 * The authenticate module in Login
 */
angular
  .module('Login.Authenticate', [
    'ui.router',
    'ui.bootstrap',
    'Itsnow.Security'
  ])

  .config(function ($stateProvider) {
    $stateProvider.state('authenticate', {
      url: '/authenticate',
      views: {
        "login": {
          controller: 'AuthenticateCtrl as authenticate',
          templateUrl: 'authenticate/authenticate.tpl.html'
        }
      },
      data: { pageTitle: '登录' }
    });
  })

  .controller('AuthenticateCtrl', ['$scope', '$rootScope', '$http',
    function ($scope, $rootScope, $http) {
      $scope.credential = {username: 'jacky.cao', password: 'secret', remember: true};

      $scope.challenge = function () {
        $http.post("/api/session?username=" + $scope.credential.username + "&password=" + $scope.credential.password)
          .success(function (data, status, headers, config) {
            window.location.href = '/index.html';
          })
          .error(function (data, status, headers, config) {
            $scope.error = data;
          });
      };
    }]);


