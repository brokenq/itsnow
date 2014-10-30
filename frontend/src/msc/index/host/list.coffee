# List accounts
angular.module('MscIndex.Host', ['ngTable','ngResource', 'dnt.action.service'])
  .config ($stateProvider)->
    $stateProvider.state 'hosts',
      url: '/hosts',
      templateUrl: 'host/list.tpl.jade'
      data: {pageTitle: '主机管理'}

  .factory('HostService', ['$resource', ($resource) ->
    $resource("/admin/api/hosts/:id", {id: "@id"})
  ])

  .controller 'HostListCtrl',['$scope', '$location', '$state', '$timeout', '$resource', 'ngTableParams', 'ActionService',
    ($scope, $location, $state, $timeout, $resource, ngTableParams, ActionService)->
      Host = $resource("/admin/api/hosts/:id", {id: "@id"})
      options =
        page:  1,           # show first page
        count: 10           # count per page
      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()) # put params in url
          Host.query(params.url(), (data, headers) ->
            $timeout(->
              params.total(headers('total'))
              $defer.resolve($scope.hosts = data)
            , 500)
          )
      $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)

      $scope.selection = {checked: false, items: {}}
      $scope.getHostById  = (id)->
        return host for host in $scope.hosts when host.id is parseInt id
      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getHostById})

      # watch for check all checkbox
      $scope.$watch 'selection.checked', (value)->
        angular.forEach $scope.hosts, (item)->
          $scope.selection.items[item.id] = value if angular.isDefined(item.id)
      # watch for data selection
      $scope.$watch('selection.items', (values) ->
        return if !$scope.hosts
        checked = 0
        unchecked = 0
        total = $scope.hosts.length
        angular.forEach $scope.hosts, (item)->
          checked   +=  ($scope.selection.items[item.id]) || 0
          unchecked += (!$scope.selection.items[item.id]) || 0
        $scope.selection.checked = (checked == total) if (unchecked == 0) || (checked == 0)
        # grayed checkbox
        angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
      , true)

      $scope.delete = (host)->
        acc = new Host(host)
        acc.$remove ->
          $scope.tableParams.reload()
  ]

