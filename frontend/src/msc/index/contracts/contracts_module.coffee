angular.module('MscIndex.Contracts', [])

.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'contracts',
    url: '/contracts'
    abstract: true
    templateUrl: 'contracts/index.tpl.jade'
    controller: 'ContractsCtrl'
    data: {pageTitle: '合同管理', default: 'contracts.list'}
  $stateProvider.state 'contracts.list',
    url: '/list'
    templateUrl: 'contracts/list.tpl.jade'
    controller: 'ContractListCtrl'
    data: {pageTitle: '合同列表'}
  $stateProvider.state 'contracts.view',
    url: '/{sn}'
    templateUrl: 'contracts/view.tpl.jade'
    controller: 'ContractViewCtrl'
    data: {pageTitle: '查看合同'}
  $stateProvider.state 'contracts.msp_accounts_view',
    url: '/{sn}/msp_account_view',
    templateUrl: 'contracts/account_view.tpl.jade',
    controller: 'ContractMspAccountViewCtrl',
    data: {pageTitle: '查看服务商帐户'}
  $stateProvider.state 'contracts.msu_accounts_view',
    url: '/{sn}/msu_account_view',
    templateUrl: 'contracts/account_view.tpl.jade',
    controller: 'ContractMsuAccountViewCtrl',
    data: {pageTitle: '查看企业帐户'}
  $urlRouterProvider.when '/contracts', '/contracts/list'

.factory('ContractService', ['$resource', ($resource) ->
    $resource "/api/contracts/:sn", {sn: '@sn'}
  ])

.filter('formatContractStatus', ->
  (status) ->
    return "邀约" if status == 'Draft'
    return "已批准" if status == 'Approved'
    return "应约" if status == 'Purposed'
    return "已拒绝" if status == 'Rejected'
)

.filter('formatTime', ->
  (time) ->
    date = new Date(time)
    return date.toLocaleString()
)

.controller('ContractsCtrl', ['$scope', '$state', '$log', 'Feedback', 'CacheService', 'ContractService',\
    ($scope, $state, $log, feedback, CacheService, contractService) ->

      $log.log "Initialized the Contracts controller"

      $scope.options =
        page: 1   # show first page
        count: 10 # count per page

      $scope.cacheService = new CacheService "sn", (value)->
        contractService.get {sn:value}
  ])

.controller('ContractListCtrl',
  ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'SelectionService', 'ContractService', \
    ($scope, $location, $log, NgTable, ActionService, SelectionService, contractService) ->

      $log.log "Initialized the Contract list controller"

      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()) # put params in url
          contractService.query params.url(), (data, headers) ->
            params.total headers('total')
            $scope.cacheService.cache data
            $defer.resolve data

      $scope.contractsTable = new NgTable(angular.extend($scope.options, $location.search()), args)
      $scope.selectionService = new SelectionService($scope.cacheService.records, "sn")
      $scope.actionService = new ActionService({watch: $scope.selectionService.items, mapping: $scope.cacheService.find})
  ])

.controller('ContractViewCtrl', ['$scope', '$stateParams', '$log', ($scope, $stateParams, $log) ->
    $scope.contract = $scope.cacheService.find $stateParams.sn, true
    $log.log "Initialized the Contract View controller on: " + JSON.stringify($scope.contract)
  ])


.controller('ContractMspAccountViewCtrl', ['$scope', '$stateParams', '$log',
    ($scope, $stateParams, $log) ->
      $scope.contract = $scope.cacheService.find $stateParams.sn, true
      $scope.account = $scope.contract.mspAccount
      $log.log "Initialized the Contract Account View controller on: " + JSON.stringify($scope.account)
  ])

.controller('ContractMsuAccountViewCtrl', ['$scope', '$stateParams', '$log',
    ($scope, $stateParams, $log) ->
      $scope.contract = $scope.cacheService.find $stateParams.sn, true
      $scope.account = $scope.contract.msuAccount
      $log.log "Initialized the Contract Account View controller on: " + JSON.stringify($scope.account)
  ])
