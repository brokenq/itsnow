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

  $stateProvider.state 'contracts.approve',
    url: '/{sn}/approve',
    templateUrl: 'contracts/approve.tpl.jade'
    controller: 'ContractApproveCtrl',
    data: {pageTitle: '批准MSP账户'}

  $stateProvider.state 'contracts.view',
    url: '/{sn}/view',
    templateUrl: 'contracts/view.tpl.jade'
    controller: 'ContractViewCtrl',
    data: {pageTitle: '查看合同'}

  $stateProvider.state 'contracts.accounts_view',
    url: '/{sn}/account_view/{account_sn}',
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

.filter 'formatContractStatus', ->
  (status)->
    switch(status)
      when 'Draft' then "邀约"
      when 'Approved' then "已批准"
      when 'Purposed' then "应约"
      when 'Rejected' then "已拒绝"
      else "无"

.filter 'formatTime', ->
  (time) ->
    date = new Date(time)
    date.toLocaleString()

.controller('ContractsCtrl', ['$scope', '$state', '$log', 'Feedback', 'CacheService', 'ContractService',\
    ($scope, $state, $log, feedback, CacheService, contractService) ->

      $scope.options =
        page: 1   # show first page
        count: 10 # count per page

      $scope.cacheService = new CacheService "sn", (value)->
        contractService.get {sn: value}

      # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
      $scope.submited = false
  ])

.controller('ContractListCtrl',
  ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'SelectionService', 'ContractService', 'Feedback',
    ($scope, $location, $log, NgTable, ActionService, SelectionService, contractService, feedback) ->

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
  ])

.controller('ContractViewCtrl', ['$scope', '$stateParams', '$log', '$filter', 'ContractService',\
    ($scope, $stateParams, $log, $filter, contractService) ->
      contractService.get({sn: $stateParams.sn}).$promise
      .then (data)->
        $scope.contract = data
        $scope.contract.createdAtFMT = $filter('date')($scope.contract.createdAt, 'yyyy-MM-dd HH:mm:ss')
        $scope.contract.statusname = $filter('formatContractStatus')($scope.contract.status)
  ])

.controller('ContractAccountViewCtrl', ['$scope', '$stateParams', '$log', \
    ($scope, $stateParams, $log) ->
      $scope.contract = $scope.cacheService.find $stateParams.sn, true
      $scope.account = mspAccount for mspAccount in $scope.contract.mspAccounts when mspAccount.sn is $stateParams.account_sn
  ])

.controller('ContractNewCtrl', ['$scope', '$state', '$log', 'Feedback', 'ContractService', 'ContractDetailService', 'ContractServiceCatalogService',\
    ($scope, $state, $log, feedback, contractService, contractDetailService, serviceCatalogService) ->

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

      $scope.openDetail = ->
        $("#bootbox").show()

      $scope.closeDetail = ->
        $("#bootbox").hide()

      $scope.details = []
      $scope.createDetail = ->
        if !$scope.newContractDetailForm.$valid
          return
        for serviceCatalog in $scope.serviceCatalogs
          if serviceCatalog.items?
            for item in serviceCatalog.items
              $scope.detail.itemId = item.id if item.ticked is true
        $scope.details.push angular.copy $scope.detail
        $("#bootbox").hide()
        $scope.detail = {}

      $scope.create = ->
        $scope.submitted=true
        contractService.save($scope.contract, (data) ->
          for detail in $scope.details
            contractDetailService.save {sn:data.sn}, detail
          feedback.success "保存合同成功"
          $state.go 'contracts.list'
        ,(resp)->
          feedback.error("保存合同失败", resp)
        )
  ])

.controller('ContractApproveCtrl', ['$scope', '$state', '$stateParams', '$log', 'Feedback', 'ContractService',
    ($scope, $state, $stateParams, $log, feedback, contractService) ->

      $scope.contract = $scope.cacheService.find $stateParams.sn, true

      $scope.mspAccounts = $scope.contract.mspAccounts
      mspAccount.ticked =true for mspAccount in $scope.mspAccounts when mspAccount.status is 'Approved'

      $scope.approve = ->

        $scope.contract.mspAccounts = []
        for mspAccount in $scope.mspAccounts
          account ={}
          account.id = mspAccount.id
          if mspAccount.ticked is true
            account.contractStatus = 'Approved'
          else
            account.contractStatus = 'Rejected'
          $scope.contract.mspAccounts.push account

        contractService.approve({sn:$scope.contract.sn}, $scope.contract).$promise
        .then(->
            feedback.success "批准成功"
            $state.go 'contracts.list'
          ,(resp)->
            feedback.error "批准失败", resp
        )
  ])

