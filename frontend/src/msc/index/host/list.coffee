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

  .controller 'HostListCtrl',['$scope', '$location', '$state', '$timeout', '$resource', 'ngTableParams', 'ActionService', 'CommonService', \
                              ($scope,   $location,   $state,   $timeout,   $resource,   ngTableParams,   ActionService,   commonService)->
    Host = $resource("/admin/api/hosts/:id", {id: "@id"})

    $scope.hosts = []
    $scope.selection = {checked: false, items: {}}
    $scope.tableParams = commonService.instanceTable(Host, $scope.hosts)
    commonService.watchSelection($scope.selection, $scope.hosts, "id")

    $scope.getHostById  = (id)->
      return host for host in $scope.hosts when host.id is parseInt id
    $scope.actionService = new ActionService {watch: $scope.selection.items, mapping: $scope.getHostById}

    $scope.refresh = ->
      $scope.tableParams.reload()
      
    $scope.delete = (host)->
      acc = new Host(host)
      acc.$remove ->
        $scope.tableParams.reload()
  ]

