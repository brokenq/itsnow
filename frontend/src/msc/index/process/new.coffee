# New account
angular.module('MscIndex.ProcessNew', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'process_new',
      url: '/processes/new',
      templateUrl: 'process/new.tpl.jade'
      data: {pageTitle: '分配服务进程'}

  .factory('AccountService', ['$resource', ($resource) ->
    $resource("/admin/api/accounts")
  ])
  .factory('HostService', ['$resource', ($resource) ->
    $resource("/admin/api/hosts")
  ])
  .controller 'ProcessNewCtrl', ['$scope', '$location', '$timeout', 'ProcessService', 'AccountService', 'HostService'
    ($scope, $location, $timeout, processService, accountService, hostService)->
      $scope.process =
        name: ""
        accountId: ""
        hostId: ""
        wd: "/opt/itsnow/" + name
        description: ""
        configuration:
          rmi_port: ""
          jmx_port: ""
          debug_port: ""
          http_port: ""
        schema:
          hostId: ""
          name: ""
          description: ""
          configuration:
            user: ""
            password: ""
            port: "3306"
      $scope.accounts = accountService.query()
      $scope.hosts = hostService.query()
      $scope.$watch 'process.name', (value)->
        $scope.process.wd = if value? then "/opt/itsnow/#{value}" else "/opt/itsnow/"
      , true

      $scope.createProcess = ->
        feedback = (content) ->
          alert content
        success = ->
          $state.go 'processes'
        failure = (response)->
          feedback response.statusText
        process = $scope.process
        rmi_port = process.configuration.rmi_port
        jmx_port = process.configuration.jmx_port
        debug_port = process.configuration.debug_port
        http_port = process.configuration.http_port
        delete process.configuration.rmi_port
        delete process.configuration.jmx_port
        delete process.configuration.debug_port
        delete process.configuration.http_port
        process.configuration['rmi.port'] = rmi_port
        process.configuration['jmx.port'] = jmx_port
        process.configuration['debug.port'] = debug_port
        process.configuration['http.port'] = http_port
        processService.save(process, success, failure)
]

