var csrfValue = function(req) {
  return (req.body && req.body._csrf)
    || (req.query && req.query._csrf)
    || (req.headers['X-CSRF-TOKEN'])
    || (req.headers['X-XSRF-TOKEN']);
};

angular.module( 'ItsNow', [
  'templates-app',
  'templates-common',
  'ItsNow.Home',
  'ItsNow.About',
  'ui.router'
])

.config( function ( $stateProvider, $urlRouterProvider ) {
  $urlRouterProvider.otherwise( '/home' );
})

.controller( 'AppCtrl', function AppCtrl ( $scope, $location ) {
  $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
    if ( angular.isDefined( toState.data.pageTitle ) ) {
      $scope.pageTitle = toState.data.pageTitle + ' | ItsNow' ;
    }
  });
})

.config(function($httpProvider, $http){
    $httpProvider.defaults.headers.post['X-CSRF-TOKEN '] = csrfValue;
    $http.defaults.headers.post['X-CSRF-TOKEN '] = csrfValue;
})

.run( function run () {
})

;
