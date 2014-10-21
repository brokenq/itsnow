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
        name: "srv2"
        address: "srv2"
        capacity: 50
        type: "DB"
        configuration:
          user: "root"
          password: "secret"
          msu_version: "0.1.9-SNAPSHOT"
          msp_version: "0.1.9-SNAPSHOT"

      $scope.types = ["DB", "APP", "COM"]
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

      $scope.checkName = (data, elem)->
        autoFillId = '#' + elem.attr 'auto-fill'
        if data.address? then $(autoFillId).val data.address else $(autoFillId).val ""
        return true
  ]

