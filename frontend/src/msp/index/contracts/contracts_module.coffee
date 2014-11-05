angular.module('MscIndex.Contracts',
  ['ngTable',
   'ngResource',
   'ngSanitize',
   'dnt.action.service',
   'Lib.Commons',
   'Lib.Utils',
   'Lib.Feedback'])
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
  $stateProvider.state 'contracts.my_list',
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
  ])

.factory('OwnContractService', ['$resource', ($resource) ->
    $resource "/api/contracts/", {},
      query: {method: 'GET', params: {own: true}, isArray: true}
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

.controller('ContractsCtrl', () ->
)
.controller('ContractListCtrl',
  ['$scope', 'ActionService', 'CommonService', 'ContractService', 'Feedback',
    ($scope, ActionService, commonService, contractService, feedback) ->
      $scope.selection = { 'checked': false, items: {} }
      $scope.contracts = []
      $scope.contractsTable = commonService.instanceTable(contractService, $scope.contracts)
      commonService.watchSelection($scope.selection, $scope.contracts, "sn")

      $scope.getContractBySn = (sn)->
        return contract for contract in $scope.contracts when contract.sn is sn
      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getContractBySn})

      $scope.accept = (contract)->
        contractService.bid {sn: contract.sn}, ()->
          feedback.success "应约成功，合同号： #{contract.sn}"
          $scope.contractsTable.reload()

  ])
.controller('ContractMyListCtrl',
  ['$scope', 'ActionService', 'CommonService', 'OwnContractService',
    ($scope, ActionService, commonService, ownContractService) ->
      $scope.selection = { 'checked': false, items: {} }
      $scope.contracts = []
      $scope.contractsTable = commonService.instanceTable(ownContractService, $scope.contracts)
      commonService.watchSelection($scope.selection, $scope.contracts, "sn")

      $scope.getContractBySn = (sn)->
        return contract for contract in $scope.contracts when contract.sn is sn
      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getContractBySn})
  ])
.controller('ContractViewCtrl', ['$scope', '$stateParams', 'ContractService', ($scope, $stateParams, contractService) ->
    contractService.get {sn: $stateParams.sn}, (data)->
      $scope.contract = data
    console.log "Initialized the SLA View controller on: " + JSON.stringify($scope.contract)
  ])

