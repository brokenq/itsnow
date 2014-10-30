angular.module('MscIndex.Schema', ['ngTable','ngResource', 'dnt.action.service'])

  .config ($stateProvider)->
    $stateProvider.state 'schemas',
      url: '/schemas',
      templateUrl: 'schema/list.tpl.jade'
      data: {pageTitle: 'Schema资源管理'}

  .factory('SchemaService', ['$resource', ($resource) ->
      $resource("/admin/api/schemas/:id")
    ])

  .controller 'SchemaListCtrl',['$scope', '$location', '$timeout', '$resource', 'ngTableParams', 'ActionService', 'Feedback',
    ($scope, $location, $timeout, $resource, ngTableParams, ActionService, Feedback)->
      Schemas = $resource("/admin/api/schemas/:id", {id: "@id"})
      options =
        page:  1,           # show first page
        count: 10           # count per page
      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()) # put params in url
          Schemas.query(params.url(), (data, headers) ->
            $timeout(->
              params.total(headers('total'))
              $defer.resolve($scope.schemas = data)
            , 500)
          )
      $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)

      $scope.selection = {checked: false, items: {}}
      $scope.getSchemaById  = (id)->
        return schema for schema in $scope.schemas when schema.id is parseInt id
      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getSchemaById})

      $scope.$watch 'selection.checked', (value)->
        angular.forEach $scope.schemas, (item)->
          $scope.selection.items[item.id] = value if angular.isDefined(item.id)
      # watch for data checkboxes
      $scope.$watch('selection.items', (values) ->
        return if !$scope.schemas
        checked = 0
        unchecked = 0
        total = $scope.schemas.length
        angular.forEach $scope.schemas, (item)->
          checked   +=  ($scope.selection.items[item.id]) || 0
          unchecked += (!$scope.selection.items[item.id]) || 0
        $scope.selection.checked = (checked == total) if (unchecked == 0) || (checked == 0)
        # grayed checkbox
        angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
      , true)

      $scope.delete = (schema)->
        acc = new Schemas(schema)
        acc.$remove ->
          $scope.tableParams.reload()
  ]