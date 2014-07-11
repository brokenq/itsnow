/**
 * Created by kadvin on 14-7-9.
 */
angular.module( 'ItsNow.Login.Forgot', [
  'ui.router',
  'ui.bootstrap',
  'SecurityService'
])

  .config(function( $stateProvider ) {
    $stateProvider.state( 'forgot', {
      url: '/forgot',
      views: {
        "login": {
          controller: 'ForgotCtrl as forgot',
          templateUrl: 'forgot/forgot.tpl.html'
        }
      },
      data:{ pageTitle: '忘记密码' }
    });
  })

  .controller( 'ForgotCtrl', function ForgotCtrl( $scope, Password ) {
  })

;
