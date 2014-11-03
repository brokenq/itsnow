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
    $resource '/api/public_service_catalogs/:sn', {sn: '@sn'},
      query: {method: 'GET', params: {keyword: '@keyword'}, isArray: true}
  ])

.controller 'ContractCtrl', ['$scope', '$state', '$stateParams', '$q', 'ContractService', 'ContractDetailService',
                             'ContractServiceCatalogService',
                             'Feedback',
  ($scope, $state, $stateParams, $q, contractService, contractDetailService, serviceCatalogService, feedback) ->

#    deferred = $q.defer

    #查询服务目录
    serviceCatalogService.query (data)->
#      deferred.resolve(data)
#      delete data.$promise
#      delete data.$resolved
      serviceCatalogs = []
      for serviceCatalog in data
        serviceCatalog.multiSelectGroup = true
        serviceCatalogs.push serviceCatalog
        serviceCatalogs.push item for item in serviceCatalog.items when serviceCatalog.items?
      $scope.serviceCatalogs = serviceCatalogs

    #the button to add a new file input
    $('#id-add-attachment').on 'click', ()->
      file = $('<input type="file" name="attachment[]" />').appendTo('#form-attachments')
      file.ace_file_input()
      file.closest('.ace-file-input')
      .addClass('width-90 inline')
      .wrap('<div class="row file-input-container"><div class="col-sm-7"></div></div>')
      .parent(/.*\.span7.*/)
      .append('<div class="action-buttons pull-right col-xs-1"><a href="#" data-action="delete" class="middle"><i class="icon-trash red bigger-130 middle"></i></a></div>')
      .find('a[data-action=delete]')
      .on 'click', (e)->
        e.preventDefault()
        $(this).closest('.row').hide 300, ()->
          $(this).remove()

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

    if $stateParams.sn? and $stateParams.sn isnt ''
#      deferred.promise.then ()->
      contractService.get({sn: $stateParams.sn}).$promise
      .then (data)->
        $scope.contract = data
        for detail in $scope.contract.details
          for serviceCatalog in $scope.serviceCatalogs
            if serviceCatalog.id is detail.item.id
              serviceCatalog.ticked = true

]
