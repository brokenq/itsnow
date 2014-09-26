# New account
angular.module('MscIndex.HostNew', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'host_new',
      url: '/hosts/new',
      templateUrl: 'host/new.tpl.jade'
      data: {pageTitle: '新增主机'}

  .controller 'HostNewCtrl', ['$scope', '$location', '$state', 'HostService',
    ($scope, $location, $state, hostService)->
      # Do nothing now
      $scope.host =
        name: ""
        address: ""
        capacity: 50
        configuration:
          user: "root"
          password: "secret"
          msu_version: "0.1.9"
          msp_version: "0.1.9"

      $scope.createHost = ->
        feedback = (content) ->
          alert content
        success = ->
          $state.go 'hosts'
        failure = (response)->
          feedback response.statusText
        host = $scope.host
        msu_version = host.configuration.msu_version
        msp_version = host.configuration.msp_version
        delete host.configuration.msu_version
        delete host.configuration.msp_version
        host.configuration['msu.version'] = msu_version
        host.configuration['msp.version'] = msp_version
        hostService.save(host, success, failure)
  ]
