/**
 * Signup Module in Login App for MSP
 */
angular.module( 'Login.Msp.Signup', [
  'ui.router',
  'ui.bootstrap',
  'Itsnow.Security'
])

  .config(function( $stateProvider ) {
    $stateProvider.state( 'msp_signup', {
      url: '/msp/signup',
      views: {
        "login": {
          controller: 'SignupCtrl as signup',
          templateUrl: 'signup/signup.tpl.html'
        }
      },
      data:{ pageTitle: '企业注册' }
    });
  })

  .controller( 'SignupCtrl', function SignupCtrl( $scope, User ) {
  })

;
