# List accounts
angular.module('MscIndex.Host', ['ngTable','ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'hosts',
      url: '/hosts',
      templateUrl: 'host/list.tpl.jade'
      data: {pageTitle: '主机管理'}

  .factory('HostService', ['$resource', ($resource) ->
    $resource("/admin/api/hosts/:id", {id: "@id"})
  ])
  .filter('formatHostStatus', ->
    (status) ->
      return "规划中" if status == 'Planing'
      return "运行中" if status == 'Running'
      return "有故障" if status == 'Abnormal'
      return "已关机" if status == 'Shutdown'
  )
  .controller 'HostListCtrl',['$scope', '$location', '$state', '$timeout', 'ngTableParams', 'HostService',($scope, $location, $state, $timeout, ngTableParams, hostService)->
    options =
      page:  1,           # show first page
      count: 10           # count per page
    args =
      total: 0,
      getData: ($defer, params) ->
        $location.search(params.url()) # put params in url
        hostService.query(params.url(), (data, headers) ->
          $timeout(->
            params.total(headers('total'))
            $defer.resolve($scope.hosts = data)
          , 500)
        )
    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)
    $scope.checkboxes = { 'checked': false, items: {} }
    # watch for check all checkbox
    $scope.$watch 'checkboxes.checked', (value)->
      angular.forEach $scope.hosts, (item)->
        $scope.checkboxes.items[item.id] = value if angular.isDefined(item.id)
    # watch for data checkboxes
    $scope.$watch('checkboxes.items', (values) ->
      return if !$scope.hosts
      checked = 0
      unchecked = 0
      total = $scope.hosts.length
      angular.forEach $scope.hosts, (item)->
        checked   +=  ($scope.checkboxes.items[item.id]) || 0
        unchecked += (!$scope.checkboxes.items[item.id]) || 0
      $scope.checkboxes.checked = (checked == total) if (unchecked == 0) || (checked == 0)
      # grayed checkbox
      angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
    , true)

    $scope.viewHost = (hostId)->
      $state.go 'host_view', {id: hostId}
  ]

