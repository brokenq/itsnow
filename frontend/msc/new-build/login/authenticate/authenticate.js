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

  .controller('AuthenticateCtrl', ['$scope', '$http', 'SessionService',
    function ($scope, $http, sessionService) {
      $scope.credential = {username: 'jacky.cao', password: 'secret', remember: true};

      $scope.challenge = function () {
        sessionService.challenge($scope.credential, function(){
          window.location.href = '/index.html';
        }, function(data){
          $scope.error = data;

        });
      };
    }]);


