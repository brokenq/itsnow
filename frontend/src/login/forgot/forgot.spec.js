/**
 * Created by kadvin on 14-7-9.
 */
describe( 'ForgotCtrl', function() {
  describe( 'isCurrentUrl', function() {
    var ForgotCtrl, MockPassword, $location, $scope;

    beforeEach( module( 'ItsNow.Login.Forgot' ) );

    beforeEach( inject( function( $controller, _$location_, $rootScope ) {
      $location = _$location_;
      $scope = $rootScope.$new();
      MockPassword = {};
      ForgotCtrl = $controller( 'ForgotCtrl', { $location: $location, $scope: $scope, Password: MockPassword});
    }));

    it( 'should pass a dummy test', inject( function() {
      expect( ForgotCtrl ).toBeTruthy();
    }));
  });
});
