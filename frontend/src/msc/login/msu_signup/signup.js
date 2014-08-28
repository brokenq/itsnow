/**
 * Signup Module for MSU in MSC Login Application.
 */
angular.module( 'Login.Msu.Signup', [
  'ui.router',
  'ui.bootstrap',
  'Itsnow.Security'
])

  .config(function( $stateProvider ) {
    $stateProvider.state( 'msu_signup', {
      url: '/msu/signup',
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
