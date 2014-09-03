/**
 * Test MSU Signup
 */
describe( 'Signup in MSC', function() {
  describe( 'loading', function() {
    var SignupCtrl, mockAccountService, $scope;

    beforeEach( module( 'MscLogin.Signup' ) );

    beforeEach( inject( function( $controller, $rootScope ) {
      $scope = $rootScope.$new();
      mockAccountService = {};
      SignupCtrl = $controller( 'SignupCtrl', { $scope: $scope, AccountService: mockAccountService });
    }));

    it( 'should pass a dummy test', inject( function() {
      expect( SignupCtrl ).toBeTruthy();
    }));
  });
});
