# View Process
angular.module('MscIndex.ProcessView', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'process_view',
      url: '/processes/{name}',
      templateUrl: 'process/view.tpl.jade'
      data: {pageTitle: '查看进程'}

  .controller 'ProcessViewCtrl', ['$scope', '$location', '$state', '$stateParams', 'ProcessService', '$http',
    ($scope, $location, $state, $stateParams, processService, $http)->
      promise = processService.get({name: $stateParams.name}).$promise
      promise.then (process) ->
        $scope.process = process
        process.configuration['rmi_port'] = process.configuration['rmi.port']
        process.configuration['jmx_port'] = process.configuration['jmx.port']
        process.configuration['debug_port'] = process.configuration['debug.port']
        process.configuration['http_port'] = process.configuration['http.port']
        if invokeId = process.configuration.createInvocationId
          url = "/admin/api/processes/" + process.name + "/follow/" + invokeId + "?offset=0"
          $http.get(url).success (log) ->
            process.creationLog = log
        if invokeId = process.configuration.startInvocationId
          url = "/admin/api/processes/" + process.name + "/follow/" + invokeId + "?offset=0"
          $http.get(url).success (log) ->
            process.startLog = log
        if invokeId = process.configuration.stopInvocationId
          url = "/admin/api/processes/" + process.name + "/follow/" + invokeId + "?offset=0"
          $http.get(url).success (log) ->
            process.stopLog = log

      $scope.deleteProcess = (processName)->
        processService.delete({name: processName}, ->
          $state.go 'processes'
        )

      $scope.startProcess = (processName)->
        url = "/admin/api/processes/" + processName + "/start"
        $http.put(url).success () ->
          $state.go 'processes'

      $scope.stopProcess = (processName)->
        url = "/admin/api/processes/" + processName + "/stop"
        $http.put(url).success () ->
          $state.go 'processes'
  ]

