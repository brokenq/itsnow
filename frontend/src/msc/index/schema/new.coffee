# New account
angular.module('MscIndex.SchemaNew', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'schema_new',
      url: '/schemas/new',
      templateUrl: 'schema/new.tpl.jade'
      data: {pageTitle: '新增Schema'}

  .controller 'schemaNewCtrl', ['$scope', '$location', '$timeout', '$state', 'SchemaService', 'HostService', '$http'
    ($scope, $location, $timeout, $state, schemaService, hostService, $http)->
      $scope.schema =
        name: ""
        hostId: ""
        configuration:
          user: ""
          password: ""
      $http.get('/admin/api/hosts/list/type/DB').success (hosts)-> $scope.hosts = hosts
      $http.get('/admin/api/hosts/list/type/COM').success (hosts)-> $scope.hosts.push hosts

      $scope.createSchema = ->
        feedback = (content) ->
          alert content
        success = ->
          $state.go 'schemas'
        failure = (response)->
          feedback response.statusText
        schemaService.save($scope.schema, success, failure)
]

