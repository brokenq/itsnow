# List accounts
angular.module('MscIndex.Account', ['ngTable','ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'index.accounts',
      url: '/accounts',
      templateUrl: 'account/list.tpl.jade'
    $stateProvider.state 'index.accounts.msu',
      url: '/msu',
      templateUrl: 'account/list.tpl.jade'
    $stateProvider.state 'index.accounts.msp',
      url: '/msp',
      templateUrl: 'account/list.tpl.jade'

  .factory('AccountService', ['$resource', ($resource) ->
    $resource("/admin/api/accounts")
  ])
  .filter('formatSubDomain', ->
    (subDomain) ->
      "http://" + subDomain + ".itsnow.com"
  )
  .filter('formatAccountStatus', ->
    (status) ->
      return "待审核" if status == 'New'
      return "已批准" if status == 'Valid'
      return "被拒绝" if status == 'Rejected'
  )
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
            $defer.resolve($scope.accounts = data)
          , 500)
        )
    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)
    $scope.checkboxes = { 'checked': false, items: {} }
    # watch for check all checkbox
    $scope.$watch 'checkboxes.checked', (value)->
      angular.forEach $scope.accounts, (item)->
        $scope.checkboxes.items[item.sn] = value if angular.isDefined(item.sn)
    # watch for data checkboxes
    $scope.$watch('checkboxes.items', (values) ->
      return if !$scope.accounts
      checked = 0
      unchecked = 0
      total = $scope.accounts.length
      angular.forEach $scope.accounts, (item)->
        checked   +=  ($scope.checkboxes.items[item.sn]) || 0
        unchecked += (!$scope.checkboxes.items[item.sn]) || 0
      $scope.checkboxes.checked = (checked == total) if (unchecked == 0) || (checked == 0)
      # grayed checkbox
      angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
    , true)
  ]

