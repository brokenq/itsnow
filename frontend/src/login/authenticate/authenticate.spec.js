/**
 * Created by kadvin on 14-7-9.
 */
describe( 'AuthenticateCtrl', function() {
  describe( 'isCurrentUrl', function() {
    var AuthenticateCtrl, $location, $scope, MockSession;

    beforeEach( module( 'ItsNow.Login.Authenticate' ) );

    beforeEach( inject( function( $controller, _$location_, $rootScope ) {
      $location = _$location_;
      $scope = $rootScope.$new();
      MockSession = {};
      AuthenticateCtrl = $controller( 'AuthenticateCtrl', { $location: $location, $scope: $scope, Session: MockSession});
    }));

    it( 'should pass a dummy test', inject( function() {
      expect( AuthenticateCtrl ).toBeTruthy();
    }));
  });
});
