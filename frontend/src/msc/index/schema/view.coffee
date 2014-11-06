# View schema
angular.module('MscIndex.SchemaView', ['ngResource'])
.config ($stateProvider)->
  $stateProvider.state 'schema_view',
    url: '/schemas/{id}',
    templateUrl: 'schema/view.tpl.jade'
    data: {pageTitle: '查看Schema'}

.controller 'SchemaViewCtrl', ['$scope', '$state', '$stateParams', '$http','SchemaService' ,
  ($scope, $state, $stateParams, $http, schemaService)->
    schemaService.get {id:$stateParams.id}, (data)->
      $scope.schema = data
]
