/**
 * Created by kadvin on 14-7-9.
 */
describe( 'MSP Staff Signup', function() {
  describe( 'loading', function() {
    var SignupCtrl, MockUser, $location, $scope;

    beforeEach( module( 'MspLogin.StaffSignup' ) );

    beforeEach( inject( function( $controller, _$location_, $rootScope ) {
      $location = _$location_;
      $scope = $rootScope.$new();
      MockUser = {};
      SignupCtrl = $controller( 'SignupCtrl', { $location: $location, $scope: $scope, User: MockUser });
    }));

    it( 'should pass a dummy test', inject( function() {
      expect( SignupCtrl ).toBeTruthy();
    }));
  });
});
