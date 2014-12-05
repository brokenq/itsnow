describe( 'Itsnow Msu Login', function() {
  describe( 'loading', function() {
    var $scope;

    beforeEach( module( 'Itsnow.MsuLogin' ) );

    beforeEach( inject( function( $controller, $rootScope ) {
      $scope = $rootScope.$new();
    }));

    it( 'should pass a dummy test', inject( function() {
      expect( $scope ).toBeTruthy();
    }));
  });
});
