angular.module('MsuIndex.ContractCreate', ['ngResource'])

.config ($stateProvider) ->
  $stateProvider.state('contract_new',
    url: '/contract/new'
    templateUrl: 'contract/new.tpl.jade'
    data:
      pageTitle: '新建合同'
  )

.factory("ContractServiceCatalogService", ["$resource", ($resource)->
    $resource '/api/public_service_catalogs/:sn', {sn: '@sn'},
      query: {method: 'GET', params: {keyword: '@keyword'}, isArray: true}
  ])

.controller 'ContractCtrl', ['$scope', '$state', '$stateParams', '$q', 'ContractService', 'ContractDetailService',
                             'ContractServiceCatalogService',
                             'Feedback',
  ($scope, $state, $stateParams, $q, contractService, contractDetailService, serviceCatalogService, feedback) ->

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
      $state.go 'contracts.contract'

    $scope.accept = () ->
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
        $state.go 'contracts.contract'
      ,(resp)->
        feedback.error("保存合同失败", resp)
      )

]
