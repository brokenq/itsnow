/**
 * Created by kadvin on 14-7-9.
 */
angular.module('ItsNow.Login.Authenticate', [
  'ui.router',
  'ui.bootstrap',
  'SecurityService'
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

  .controller('AuthenticateCtrl', ['$scope', '$location', 'Session',
    function ($scope, $location, Session) {
      // This is simple a demo for UI Bootstrap.
      $scope.challenger = {username: 'admin', password: 'secret', remember: true};
      $scope.error = null;

      $scope.challenge = function (input) {
        Session.create(input, function (value, header) {
          $location.path("/index.html");
        }, function (response) {
          $scope.error = response.data;
        });
      };
      //有了这个成员，模板中才能写成: authenticate.challenge(input)
      //this.challenge = $scope.challenge;
    }])
;
