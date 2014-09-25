# View Process
angular.module('MscIndex.ProcessView', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'processes_view',
      url: '/processes/view',
      templateUrl: 'processes/view.tpl.jade'
      data: {pageTitle: '查看进程'}

  .controller 'ProcessViewCtrl', ['$scope', '$location', '$state', 'ProcessService',
    ($scope, $location, $state, ProcessService)->
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

      $scope.process =

      $scope.submit ->
        feedback = (content) ->
          alert content
        success = ->
          $state.go 'hosts'
        failure = (response)->
          feedback response.statusText
        hostService.save($scope.host, success, failure)
  ]

