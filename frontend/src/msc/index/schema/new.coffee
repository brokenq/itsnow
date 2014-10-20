# New account
angular.module('MscIndex.SchemaNew', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'schema_new',
      url: '/schemas/new',
      templateUrl: 'schema/new.tpl.jade'
      data: {pageTitle: '新增Schema'}

  .controller 'schemaNewCtrl', ['$scope', '$location', '$timeout', '$state', 'SchemaService', 'HostService'
    ($scope, $location, $timeout, $state, schemaService, hostService)->
      $scope.schema =
        name: ""
        hostId: ""
        configuration:
          user: ""
          password: ""
      $scope.hosts = hostService.query()

      $scope.createSchema = ->
        feedback = (content) ->
          alert content
        success = ->
          $state.go 'schemas'
        failure = (response)->
          feedback response.statusText
        schemaService.save($scope.schema, success, failure)
]

