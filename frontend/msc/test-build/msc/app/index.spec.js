describe( 'Itsnow Msc App', function() {
  describe( 'loading', function() {
    var $scope;

    beforeEach( module( 'Itsnow.MscApp' ) );

    beforeEach( inject( function( $controller, $rootScope ) {
      $scope = $rootScope.$new();
    }));

    it( 'should pass a dummy test', inject( function() {
      expect( $scope ).toBeTruthy();
    }));
  });
});
