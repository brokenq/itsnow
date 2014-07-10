/**
 * Created by kadvin on 14-7-2.
 */
describe( 'LoginCtrl', function() {
  describe( 'isCurrentUrl', function() {
    var LoginCtrl, $location, $scope;

    beforeEach( module( 'ItsNow.Login' ) );

    beforeEach( inject( function( $controller, _$location_, $rootScope, $resource ) {
      $location = _$location_;
      $scope = $rootScope.$new();
      LoginCtrl = $controller( 'LoginCtrl', { $location: $location, $scope: $scope, $resource: $resource });
    }));

    it( 'should pass a dummy test', inject( function() {
      expect( LoginCtrl ).toBeTruthy();
    }));
  });
});
