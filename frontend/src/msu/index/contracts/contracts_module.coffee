angular.module('MsuIndex.Contracts', ['multi-select'])
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
    data: {pageTitle: '合同列表'}
  $stateProvider.state 'contracts.new',
    url: '/new',
    templateUrl: 'contracts/new.tpl.jade'
    controller: 'ContractNewCtrl',
    data: {pageTitle: '新增合同'}
  $stateProvider.state 'contracts.view',
    url: '/{sn}/view',
    templateUrl: 'contracts/view.tpl.jade'
    controller: 'ContractViewCtrl',
    data: {pageTitle: '查看合同'}
  $stateProvider.state 'contracts.accounts_view',
    url: '/{sn}/account_view',
    templateUrl: 'contracts/account_view.tpl.jade',
    controller: 'ContractAccountViewCtrl',
    data: {pageTitle: '查看帐户'}
  $urlRouterProvider.when '/contracts', '/contracts/list'

.factory('ContractService', ['$resource', ($resource) ->
    $resource '/api/contracts/:sn/:result/:do', {},
      save: {method: 'POST'}
      update: {method: 'PUT', params: {result:'user', do: 'relation'}}
      get: {method: 'GET', params: {sn: '@sn'}}
      query: {method: 'GET', params: {keyword: '@keyword'}, isArray: true}
      reject: {method: 'PUT', params: {sn: '@sn', do: 'reject'}}
      approve: {method: 'PUT', params: {sn: '@sn', do: 'approve'}}
      list: {method: 'GET', params: {result: 'users', do:'belongs_to_account'}, isArray: true}
      getLoginUser: {method: 'GET', params: {result: 'users', do:'login'}, isArray: true}
  ])

.factory('ContractDetailService', ['$resource', ($resource) ->
    $resource '/api/contracts/:sn/details', {sn: '@sn'}
  ])

.factory("ContractServiceCatalogService", ["$resource", ($resource)->
    $resource '/api/public_service_catalogs/:sn', {sn: '@sn'},
      query: {method: 'GET', params: {keyword: '@keyword'}, isArray: true}
  ])

.filter 'formatContractStatus', () ->
  (status)->
    switch(status)
      when 'Draft' then "邀约"
      when 'Approved' then "已批准"
      when 'Purposed' then "应约"
      when 'Rejected' then "已拒绝"
      else "无"

.filter 'formatTime', () ->
  (time) ->
    date = new Date(time)
    date.toLocaleString()

.controller('ContractsCtrl', ['$scope', '$state', '$log', 'Feedback', 'CacheService', 'ContractService',\
    ($scope, $state, $log, feedback, CacheService, contractService) ->
      # frontend controller logic
      $log.log "Initialized the Contracts controller"
      $scope.options =
        page: 1, # show first page
        count: 10 # count per page

      $scope.cacheService = new CacheService "sn", (value)->
        contractService.get {sn: value}

      # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
      $scope.submited = false
  ])

.controller('ContractListCtrl',
  ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'SelectionService', 'ContractService', 'Feedback',
    ($scope, $location, $log, NgTable, ActionService, SelectionService, contractService, feedback) ->
      $log.log "Initialized the Contract list controller"

      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()); # put params in url
          contractService.query params.url(), (data, headers) ->
            params.total headers('total')
            $scope.cacheService.cache data
            $defer.resolve $scope.contracts = data

      $scope.contractsTable = new NgTable(angular.extend($scope.options, $location.search()), args)
      $scope.selectionService = new SelectionService($scope.cacheService.records, "sn")
      $scope.actionService = new ActionService({watch: $scope.selectionService.items, mapping: $scope.cacheService.find})

      $scope.approve = (contract) ->
        contractService.approve({sn:contract.sn}
        , ()->
          feedback.success "批准成功"
          $scope.contractsTable.reload()
        , (resp)->
          feedback.error "批准失败", resp )

      $scope.reject = (contract) ->
        contractService.reject({sn:contract.sn}
        , ()->
          feedback.success "拒绝成功"
          $scope.contractsTable.reload()
        , (resp)->
          feedback.error "拒绝失败", resp )
  ])

.controller('ContractViewCtrl', ['$scope', '$stateParams', '$log', ($scope, $stateParams, $log) ->
    $scope.contract = $scope.cacheService.find $stateParams.sn, true
    $log.log "Initialized the Contract View controller on: " + JSON.stringify($scope.contract)
  ])

.controller('ContractAccountViewCtrl', ['$scope', '$stateParams', '$log', \
    ($scope, $stateParams, $log) ->
      $scope.contract = $scope.cacheService.find $stateParams.sn, true
      $scope.account = $scope.contract.mspAccount
      $log.log "Initialized the Contract Account View controller on: " + JSON.stringify($scope.account)
  ])

.controller('ContractNewCtrl', ['$scope', '$state', '$log', 'Feedback', 'ContractService', 'ContractDetailService', 'ContractServiceCatalogService',\
    ($scope, $state, $log, feedback, contractService, contractDetailService, serviceCatalogService) ->
      $log.log "Initialized the Contract New controller"

      #查询服务目录
      serviceCatalogService.query (data)->
        serviceCatalogs = []
        closeGroup = {}
        for serviceCatalog in data
          serviceCatalog.multiSelectGroup = true
          serviceCatalogs.push serviceCatalog
          serviceCatalogs.push item for item in serviceCatalog.items when serviceCatalog.items?
          closeGroup.multiSelectGroup = false
          serviceCatalogs.push closeGroup
        $scope.serviceCatalogs = serviceCatalogs

      $scope.cancel = () ->
        $state.go 'contracts.list'

      $scope.create = () ->
        for serviceCatalog in $scope.serviceCatalogs
          if serviceCatalog.items?
            for item in serviceCatalog.items
              $scope.detail.itemId = item.id if item.ticked is true
        details = []
        details.push $scope.detail
        contractService.save($scope.contract, (data) ->
          for detail in details
            contractDetailService.save {sn:data.sn}, detail
          feedback.success "保存合同成功"
          $state.go 'contracts.list'
        ,(resp)->
          feedback.error("保存合同失败", resp)
        )
  ])



