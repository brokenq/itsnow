# New account
angular.module('MscIndex.HostView', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'host_view',
      url: '/hosts/{id}',
      templateUrl: 'host/view.tpl.jade'
      data: {pageTitle: '查看主机'}

  .controller 'HostViewCtrl', ['$scope', '$state', '$stateParams', 'HostService', '$http',
    ($scope, $state, $stateParams, hostService, $http)->
      #hostService.query (data)->
        #$scope.host = data
      promise = hostService.get({id: $stateParams.id}).$promise
      promise.then (host) ->
        $scope.host = host
        if invokeId = host.configuration.createInvocationId
          url = "/admin/api/hosts/" + host.id + "/follow/" + invokeId + "?offset=0"
          $http.get(url).success (log) ->
            host.creationLog = log
      # Do nothing now
      $scope.deleteHost = (hostId)->
        hostService.delete({id: hostId}, ->
          $state.go 'hosts'
        )

  ]

