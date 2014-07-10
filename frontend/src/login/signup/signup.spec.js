/**
 * Created by kadvin on 14-7-9.
 */
describe( 'SignupCtrl', function() {
  describe( 'isCurrentUrl', function() {
    var SignupCtrl, MockUser, $location, $scope;

    beforeEach( module( 'ItsNow.Login.Signup' ) );

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
