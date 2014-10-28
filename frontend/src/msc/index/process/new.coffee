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
      $scope.schemas = schemaService.query()

      $scope.$watch 'account.id', (account)->
        if account?
          $http.get("/admin/api/processes/auto_new/#{account.sn}").success((process)->
            $scope.process = process
            $scope.schemas.push process.schema
            $("#process_schema").val(process.schema)
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

