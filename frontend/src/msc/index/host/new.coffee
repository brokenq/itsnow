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
          'msu_version': "0.1.9"
          'msp_version': "0.1.9"

      $scope.submit ->
        feedback = (content) ->
          alert content
        success = ->
          $state.go 'hosts'
        failure = (response)->
          feedback response.statusText
        hostService.save($scope.host, success, failure)
  ]

