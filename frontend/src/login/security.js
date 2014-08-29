angular
  .module('Itsnow.Security', [])

  .factory('Session', ['$resource',
    function ($resource) {
      return $resource("/api/session?username=:username&password=:password", {username: '@username', password: '@password'}, {
        create: {method: 'POST'}, /* login */
        destroy: {method: 'DELETE'}, /* logout */
        current: {method: 'GET'}, /* current session */
        profile: {method: 'GET'}  /* current user profile */
      });
    }
  ])

  .factory('Password', ['$resource'],
    function($resource){
      return $resource("api/password", {}, {
        forgot: {method: 'POST'},
        reset: {method: 'PUT'}
      });
    }
  )

  .factory('User', ['$resource'],
    function($resource){
      return $resource('api/users/:userId', {}, {
        signup: {url: 'users', method: 'POST', params:{userId:'@userId'}}
      });
    }
  );
