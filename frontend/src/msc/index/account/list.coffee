# List accounts
angular.module('MscIndex.Account', ['ngTable','ngResource'])
  .factory('AccountService', ['$resource', ($resource) ->
    $resource("/admin/api/accounts")
  ])
  .controller 'AccountListCtrl', ['$scope', '$location', '$timeout', 'ngTableParams', 'AccountService', ($scope, $location, $timeout, ngTableParams, accountService)->
    $scope.tableParams = new ngTableParams(
#    merge default params with url
      angular.extend({
        page: 1,           # show first page
        size: 10,          # count per page
        sort: {sn: 'asc'}  # initial sorting
      }, $location.search()),
      {total: 0, getData: ($defer, params) ->
        $location.search(params.url()); # put params in url
        accountService.query(params.url(), (data, headers) ->
          $timeout(->
            params.total(headers('total'))
            $defer.resolve(data)
          , 500)
        )
      }
    )
  ]