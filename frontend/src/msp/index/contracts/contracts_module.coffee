angular.module('MspIndex.Contracts', [])

.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'contracts',
    url: '/contracts',
    abstract: true,
    templateUrl: 'contracts/index.tpl.jade',
    controller: 'ContractsCtrl',
    data: {pageTitle: '合同管理', default: 'contracts.list'}
  $stateProvider.state 'contracts.list',
    url: '/list',
    templateUrl: 'contracts/list.tpl.jade'
    controller: 'ContractListCtrl',
    data: {pageTitle: '邀约管理'}
  $stateProvider.state 'contracts.my-list',
    url: '/my_list',
    templateUrl: 'contracts/my_list.tpl.jade'
    controller: 'ContractMyListCtrl',
    data: {pageTitle: '我的合约'}
  $stateProvider.state 'contracts.view',
    url: '/{sn}',
    templateUrl: 'contracts/view.tpl.jade'
    controller: 'ContractViewCtrl',
    data: {pageTitle: '查看合约'}
  $urlRouterProvider.when '/contracts', '/contracts/list'

.factory('ContractService', ['$resource', ($resource) ->
    $resource "/api/contracts/:sn/:bid", {},
      query: {method: 'GET', params: {keyword: '@keyword'}, isArray: true}
      get: {method: 'GET', params: {sn: '@sn'}}
      bid: {method: 'PUT', params: {sn: '@sn', bid: 'bid'}}
      ownQuery: {method: 'GET', params: {keyword: '@keyword', own: true}, isArray: true}
  ])

.filter 'formatContractStatus', () ->
  (status)->
    switch(status)
      when 'Draft' then "邀约"
      when 'Approved' then "已批准"
      when 'Purposed' then "应约"
      when 'Rejected' then "被拒绝"
      else
        "无"

.filter 'formatTime', () ->
  (time) ->
    date = new Date(time)
    date.toLocaleString()

.controller('ContractsCtrl', ['$scope', '$log', 'CacheService', 'ContractService',\
    ($scope, $log, CacheService, contractService) ->
      # frontend controller logic
      $log.log "Initialized the Contracts controller"
      $scope.options =
        page: 1   # show first page
        count: 10 # count per page

      $scope.cacheService = new CacheService "sn", (value)->
        contractService.get {sn: value}
  ])

.controller('ContractListCtrl',
  ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'SelectionService', 'ContractService', 'Feedback',\
    ($scope, $location, $log, NgTable, ActionService, SelectionService, contractService, feedback) ->
      $log.log "Initialized the Contracts list controller"

      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()); # put params in url
          contractService.query params.url(), (data, headers) ->
            params.total headers('total')
            $scope.cacheService.cache data
            $defer.resolve data

      $scope.contractsTable = new NgTable(angular.extend($scope.options, $location.search()), args)
      $scope.selectionService = new SelectionService($scope.cacheService.records, "sn")
      $scope.actionService = new ActionService({watch: $scope.selectionService.items, mapping: $scope.cacheService.find})

      $scope.accept = (contract)->
        contractService.bid {sn: contract.sn}
        , ()->
          feedback.success "应约成功，合同号： #{contract.sn}"
          $scope.contractsTable.reload()
        , (resp)->
          feedback.error "应约失败", resp
  ])

.controller('ContractMyListCtrl',
  ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'SelectionService', 'ContractService',\
    ($scope, $location, $log, NgTable, ActionService, SelectionService, contractService) ->
      $log.log "Initialized the Contracts list controller"

      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()); # put params in url
          contractService.ownQuery params.url(), (data, headers) ->
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

