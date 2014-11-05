angular.module('MsuIndex.Contract', ['ngTable', 'ngResource', 'Lib.Feedback'])

.config ($stateProvider) ->
  $stateProvider.state('contracts',
    url: '/contracts'
    templateUrl: 'contract/list.tpl.jade'
    data:
      pageTitle: '合同管理'
  ).state('contracts.contract',
    url: '/contract'
    templateUrl: 'contract/list.tpl.jade'
    data:
      pageTitle: '我的合同'
  )

.factory('ContractService', ['$resource', ($resource) ->
    $resource '/api/contracts/:sn/:do', {},
      save: {method: 'POST'}
      get: {method: 'GET', params: {sn: '@sn'}}
      query: {method: 'GET', params: {keyword: '@keyword'}, isArray: true}
      reject: {method: 'PUT', params: {sn: '@sn', do: 'reject'}}
      approve: {method: 'PUT', params: {sn: '@sn', do: 'approve'}}
])

.factory('ContractDetailService', ['$resource', ($resource) ->
    $resource '/api/contracts/:sn/details', {sn: '@sn'}
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

.controller 'ContractListCtrl', ['$scope', '$location','$state', '$timeout', 'ngTableParams', 'ContractService', 'ActionService', 'Feedback',
    ($scope, $location, $state, $timeout, NgTableParams, contractService, ActionService, feedback) ->

      options =
        page: 1 # show first page
        count: 5 # count per page
      args =
        total: 0
        getData: ($defer, params) ->
          $location.search(params.url()) # put params in url
          contractService.query(params.url(), (data, headers) ->
            $timeout(->
              params.total(headers('total'))
              $defer.resolve($scope.contracts = data)
            , 500)
          )

      $scope.tableParams = new NgTableParams(angular.extend(options, $location.search()), args)
      $scope.checkboxes = { 'checked': false, items: {} }
      $scope.getContractBySn = (sn)->
        return contract for contract in $scope.contracts when contract.sn is sn
      $scope.actionService = new ActionService({watch: $scope.checkboxes.items, mapping: $scope.getContractBySn})

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
          checked += ($scope.checkboxes.items[item.sn]) || 0
          unchecked += (!$scope.checkboxes.items[item.sn]) || 0
        $scope.checkboxes.checked = (checked == total) if (unchecked == 0) || (checked == 0)
        # grayed checkbox
        angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0))
      , true)

      $scope.approve = (contract) ->
        contractService.approve({sn:contract.sn}
        , ()->
          feedback.success "批准成功"
          $scope.tableParams.reload()
        , (resp)->
          feedback.error "批准失败", resp )

      $scope.reject = (contract) ->
        contractService.reject({sn:contract.sn}
        , ()->
          feedback.success "拒绝成功"
          $scope.tableParams.reload()
        , (resp)->
          feedback.error "拒绝失败", resp )

      $scope.search = ($event) ->
        if $event.keyCode is 13
          contractService.query {keyword: $event.currentTarget.value}, (data)->
            $scope.contracts = data

]
