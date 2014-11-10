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
    data: {pageTitle: '合同列表'}
  $stateProvider.state 'contracts.view',
    url: '/{sn}',
    templateUrl: 'contracts/view.tpl.jade'
    controller: 'ContractViewCtrl',
    data: {pageTitle: '查看合同'}
  $urlRouterProvider.when '/contracts', '/contracts/list'

.factory('ContractService', ['$resource', ($resource) ->
    $resource("/admin/api/contracts", {}, {
      query: { method: 'GET', params: {keyword: '@keyword'}, isArray: true}
    });
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

.controller('ContractsCtrl', ['$scope', '$state', '$log', 'Feedback', 'CacheService',
    ($scope, $state, $log, feedback, CacheService) ->
      $log.log "Initialized the Contracts controller"
      $scope.options =
        page: 1, # show first page
        count: 10 # count per page

      $scope.cacheService = new CacheService("sn")
  ])

.controller('ContractListCtrl',
  ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'CommonService', 'ContractService', 'Feedback',
    ($scope, $location, $log, NgTable, ActionService, commonService, contractService, feedback) ->
      $log.log "Initialized the Contract list controller"

      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()); # put params in url
          contractService.query params.url(), (data, headers) ->
            params.total headers('total')
            $scope.cacheService.cache data
            $defer.resolve data

      $scope.selection = { checked: false, items: {} }
      $scope.contractsTable = new NgTable(angular.extend($scope.options, $location.search()), args);
      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.cacheService.find})
      commonService.watchSelection($scope.selection, $scope.cacheService.records, "sn")
  ])

.controller('ContractViewCtrl', ['$scope', '$stateParams', '$log', ($scope, $stateParams, $log) ->
    $scope.contract = $scope.cacheService.find $stateParams.sn, true
    $log.log "Initialized the Contract View controller on: " + JSON.stringify($scope.contract)
  ])
