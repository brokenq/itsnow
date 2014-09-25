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
        account_id: ""
        host_id: ""
        wd: "/opt/itsnow/" + name
        description: ""
        configuration:
          rmi_port: ""
          jmx_port: ""
          debug_port: ""
          http_port: ""
        schema:
          host_id: ""
          name: ""
          description: ""
          configuration:
            user: ""
            password: ""
            port: "3306"
      $scope.accounts = accountService.query()
      $scope.hosts = hostService.query()
      $scope.$watch 'process.name', (value)->
        $scope.process.wd = if value? "/opt/itsnow/" + value then "/opt/itsnow/"
      , true
]

