angular.module('MsuIndex.ContractCreate', ['ngResource'])

.config ($stateProvider) ->
  $stateProvider.state('contract_edit',
    url: '/contract/{sn}/edit'
    templateUrl: 'contract/edit.tpl.jade'
    data:
      pageTitle: '合同管理'
  ).state('contract_new',
    url: '/contract/new'
    templateUrl: 'contract/new.tpl.jade'
    data:
      pageTitle: '合同管理'
  )

.factory("ContractServiceCatalogService", ["$resource", ($resource)->
    $resource '/api/public_service_catalogs/:sn', {},
      get: {method: 'GET', params: {sn: '@sn'}}
      query: {method: 'GET', isArray: true}
  ])

.controller 'ContractCtrl', ['$scope', '$state', '$stateParams', 'ContractService', 'ContractServiceCatalogService', 'Feedback',
  ($scope, $state, $stateParams, contractService, serviceCatalogService, feedback) ->

    #查询服务目录
    serviceCatalogService.query (data)->
      $scope.serviceCatalog = data

    $scope.createContract = () ->
      contractService.save $scope.contract, () ->
        feedback.success "保存合同成功"
        $state.go 'contracts.contract'

]
