# List contracts
angular.module('MspIndex.Contract', ['ngTable','ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'contracts',
      url: '/contracts',
      templateUrl: 'contract/list.tpl.jade'
      data: {pageTitle: '合同管理'}
    $stateProvider.state 'contracts.contract',
      url: '/contract',
      templateUrl: 'contract/list.tpl.jade'
      data: {pageTitle: '合同管理'}

  .factory('ContractService', ['$resource', ($resource) ->
    $resource("/api/contracts")
  ])
  .filter('formatContractStatus', ->
    (status) ->
      return "邀约" if status == 'Draft'
      return "已批准" if status == 'Valid'
      return "应约" if status == 'Purposed'
      return "被拒绝" if status == 'Rejected'
  )
.filter('formatTime', ->
  (time) ->
    date = new Date(time)
    return date.toLocaleString()
)
  .controller 'ContractListCtrl',['$scope', '$location', '$timeout', 'ngTableParams', 'ContractService',($scope, $location, $timeout, ngTableParams, contractService)->
    options =
      page:  1,           # show first page
      count: 10           # count per page
    args =
      total: 0,
      getData: ($defer, params) ->
        $location.search(params.url()) # put params in url
        contractService.query(params.url(), (data, headers) ->
          $timeout(->
            params.total(headers('total'))
            $defer.resolve($scope.contracts = data)
          , 500)
        )
    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)
    $scope.checkboxes = { 'checked': false, items: {} }
    # watch for check all checkbox
    $scope.$watch 'checkboxes.checked', (value)->
      angular.forEach $scope.contracts, (item)->
        $scope.checkboxes.items[item.sn] = value if angular.isDefined(item.sn)
    # watch for data checkboxes
    $scope.$watch('checkboxes.items', (values) ->
      return if !$scope.contracts
      checked = 0
      unchecked = 0
      total = $scope.contracts.length
      angular.forEach $scope.contracts, (item)->
        checked   +=  ($scope.checkboxes.items[item.sn]) || 0
        unchecked += (!$scope.checkboxes.items[item.sn]) || 0
      $scope.checkboxes.checked = (checked == total) if (unchecked == 0) || (checked == 0)
      # grayed checkbox
      angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
    , true)
  ]

