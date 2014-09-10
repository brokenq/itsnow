# List accounts
angular.module('MscIndex.Account', ['ngTable','ngResource'])
  .factory('AccountService', ['$resource', ($resource) ->
    $resource("/admin/api/accounts")
  ])
  .controller 'AccountListCtrl',['$scope', '$location', '$timeout', 'ngTableParams', 'AccountService',($scope, $location, $timeout, ngTableParams, accountService)->
    options =
      page:  1,           # show first page
      count: 10           # count per page
    args =
      total: 0,
      getData: ($defer, params) ->
        $location.search(params.url()) # put params in url
        accountService.query(params.url(), (data, headers) ->
          $timeout(->
            params.total(headers('total'))
            $defer.resolve(data)
          , 500)
        )
    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)
  ]