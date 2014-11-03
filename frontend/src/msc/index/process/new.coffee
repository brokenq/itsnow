# New account
angular.module('MscIndex.ProcessNew', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'process_new',
      url: '/processes/new',
      templateUrl: 'process/new.tpl.jade'
      data: {pageTitle: '分配服务进程'}

  .controller 'ProcessNewCtrl', ['$scope', '$location', '$timeout', '$state', 'ProcessService', 'AccountService', '$http', 'SchemaService',
    ($scope, $location, $timeout, $state, processService, accountService, $http, schemaService)->
      $http.get("/admin/api/accounts/list_no_process").success((accounts)->
        $scope.accounts = accounts
      )
      schemaService.query (schemas)->
        $scope.schemas = schemas
      $scope.hosts = []
      $http.get("/admin/api/hosts/list/type/APP").success (hosts)->
        $scope.hosts = hosts if hosts? and hosts.length > 0
        $http.get("/admin/api/hosts/list/type/COM").success (hosts)->
          $scope.hosts = $scope.hosts.concat hosts if hosts? and hosts.length > 0

      getHostById = (id)->
        return host for host in $scope.hosts when host.id is parseInt id
      $scope.$watch 'account.id', (account)->
        if account?
          $http.get("/admin/api/processes/auto_new/#{account.sn}").success((process)->
            $scope.process = process
            $scope.schemas.push process.schema
            $scope.process.schema = process.schema
            $scope.process.host = getHostById process.host.id
          )

      $scope.createProcess = ->
        feedback = (content) ->
          alert content
        success = ->
          $state.go 'processes'
        failure = (response)->
          feedback response.statusText
        process = $scope.process
        processService.save(process, success, failure)
]

