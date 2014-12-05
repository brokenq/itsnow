/**
 * Created by kadvin on 14-7-9.
 */
angular.module( 'MspLogin.StaffSignup', [
  'ui.router',
  'ui.bootstrap',
  'Itsnow.Security'
])

  .config(function( $stateProvider ) {
    $stateProvider.state( 'signup', {
      url: '/signup',
      views: {
        "login": {
          controller: 'SignupCtrl as signup',
          templateUrl: 'signup/signup.tpl.html'
        }
      },
      data:{ pageTitle: '新用户注册' }
    });
  })

  .controller( 'SignupCtrl', function SignupCtrl( $scope, User ) {
  })

;
