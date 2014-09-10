describe( 'Itsnow Msu App', function() {
  describe( 'loading', function() {
    var $scope;

    beforeEach( module( 'Itsnow.MsuApp' ) );

    beforeEach( inject( function( $controller, $rootScope ) {
      $scope = $rootScope.$new();
    }));

    it( 'should pass a dummy test', inject( function() {
      expect( $scope ).toBeTruthy();
    }));
  });
});
